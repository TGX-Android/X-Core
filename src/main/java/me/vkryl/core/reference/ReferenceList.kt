/*
 * This file is a part of X-Core
 * Copyright © Vyacheslav Krylov (slavone@protonmail.ch) 2014-2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File created on 26/02/2018
 */
package me.vkryl.core.reference

import java.lang.ref.Reference
import java.util.concurrent.Semaphore

/**
 * Object that keeps references to items and allows modifying itself during iteration.
 *
 * All methods are thread-safe, except [.iterator].
 * If there's chance you will call [.iterator] from multiple threads, pass true to a constructor.
 *
 * By default, [ReferenceCreator.newReference] creates [java.lang.ref.WeakReference].
 * However, this behavior can be overridden.
 *
 * [.iterator] will throw [IllegalStateException] if previous iteration has not been completed
 * [.iterator] will wait until previous iteration finishes, when using `isThreadSafe` in constructor
 * [.iterator] will throw [IllegalStateException] when [InterruptedException] gets caught
 * [.iterator] will cause deadlock or ANR if you try to iterate inside iteration
 *
 * [Iterator.next] iterates from newer to older items
 * [Iterator.next] will not return null
 * [Iterator.next] will not return items removed during iteration
 * [Iterator.next] will not return items added during iteration, they will be available on the next iteration
 */
class ReferenceList<T> @JvmOverloads constructor(
  isThreadSafe: Boolean = false,
  cacheIterator: Boolean = true,
  fullnessListener: FullnessListener? = null
) : Iterable<T>, ReferenceCreator<T> {

  private val cacheIterator: Boolean
  private val items: MutableList<Reference<T>>
  private val itemsToRemove: MutableList<Reference<T>> = ArrayList()
  private val itemsToAdd: MutableList<Reference<T>> = ArrayList()
  private var isLocked = false
  private val semaphore: Semaphore?
  private var isFull = false
  private val fullnessListener: FullnessListener?
  var next: ReferenceList<T>? = null // Used by ReferenceMap<T>

  init {
    semaphore = if (isThreadSafe) Semaphore(1) else null
    this.cacheIterator = cacheIterator
    items = ArrayList()
    this.fullnessListener = fullnessListener
  }

  private fun checkFull() {
    if (fullnessListener != null) {
      val isFull = items.isNotEmpty()
      if (this.isFull != isFull) {
        this.isFull = isFull
        fullnessListener.onFullnessStateChanged(this, isFull)
      }
    }
  }

  private fun lock() {
    check(!isLocked)
    isLocked = true
  }

  private fun unlock() {
    check(isLocked)
    isLocked = false
    if (itemsToRemove.isNotEmpty()) {
      items.removeAll(itemsToRemove)
      itemsToRemove.clear()
    }
    if (itemsToAdd.isNotEmpty()) {
      items.addAll(itemsToAdd)
      itemsToAdd.clear()
    }
    checkFull()
  }

  private fun indexOf(item: T?): Int {
    if (item == null) {
      return -1
    }
    val size = items.size
    for (i in size - 1 downTo 0) {
      val existingItem = items[i].get()
      if (existingItem === item) {
        return i
      }
    }
    return -1
  }

  /**
   * @return Number of added elements
   */
  fun addAll(itemsToAdd: ReferenceList<T>): Int {
    var count = 0
    for (item in itemsToAdd) {
      if (add(item)) count++
    }
    return count
  }

  /**
   * @return False when item is already present in the list
   */
  fun add(itemToAdd: T): Boolean {
    synchronized(items) {
      val i = indexOf(itemToAdd)
      if (i != -1) {
        return false
      }
      return if (isLocked) {
        val isAdded = ReferenceUtils.addReference(this, itemsToAdd, itemToAdd)
        ReferenceUtils.removeReference(itemsToRemove, itemToAdd)
        isAdded
      } else {
        val reference: Reference<T> = newReference(itemToAdd)
        items.add(reference)
        checkFull()
        true
      }
    }
  }

  fun remove(itemToRemove: T): Boolean {
    synchronized(items) {
      val i = indexOf(itemToRemove)
      if (i == -1) {
        return false
      }
      if (isLocked) {
        val item = items[i]
        if (!itemsToRemove.contains(item)) {
          itemsToRemove.add(item)
        }
        ReferenceUtils.removeReference(itemsToAdd, item.get())
      } else {
        items.removeAt(i)
        checkFull()
      }
      return true
    }
  }

  fun clear() {
    synchronized(items) {
      if (isLocked) {
        for (item in items) {
          if (!itemsToRemove.contains(item)) {
            itemsToRemove.add(item)
          }
          ReferenceUtils.removeReference(itemsToAdd, item.get())
        }
      } else {
        items.clear()
        checkFull()
      }
    }
  }

  val isEmpty: Boolean
    get() {
      synchronized(items) {
        return if (isLocked) {
          items.isEmpty() && itemsToAdd.isEmpty()
        } else {
          ReferenceUtils.gcReferenceList(items)
          items.isEmpty()
        }
      }
    }

  fun hasReferences(): Boolean {
    synchronized(items) {
      return if (isLocked) {
        items.size != 0 || itemsToAdd.size != 0
      } else {
        items.isNotEmpty()
      }
    }
  }

  private var itr: Itr? = null
  override fun iterator(): Iterator<T> {
    if (semaphore != null) {
      try {
        semaphore.acquire()
      } catch (t: InterruptedException) {
        throw IllegalStateException()
      }
    }
    synchronized(items) {
      return if (cacheIterator) ({
        lock()
        if (itr == null) {
          itr = Itr()
        } else {
          itr?.index = items.size
          itr?.nextItem = null
        }
        itr
      })!! else Itr()
    }
  }

  private inner class Itr : Iterator<T> {

    var index = items.size
    var nextItem: T? = null

    override fun hasNext(): Boolean {
      synchronized(items) {
        nextItem = null
        while (nextItem == null && index > 0) {
          val reference = items[--index]
          val item = reference.get()
          if (item != null && !itemsToRemove.contains(reference)) {
            nextItem = item
            break
          }
        }
        if (nextItem == null) {
          if (cacheIterator) {
            unlock()
          }
        }
      }
      if (nextItem == null) {
        semaphore?.release()
        return false
      }
      return true
    }

    override fun next(): T {
      if (nextItem == null) {
        throw NoSuchElementException()
      }
      return nextItem!!
    }
  }

  fun interface FullnessListener {
    fun onFullnessStateChanged(list: ReferenceList<*>?, isFull: Boolean)
  }
}