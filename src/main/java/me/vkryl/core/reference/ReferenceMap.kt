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

import java.util.HashMap

open class ReferenceMap<K, T> @JvmOverloads constructor(
  private val isThreadSafe: Boolean = false,
  private val cacheIterator: Boolean = true,
  fullnessListener: FullnessListener<K, T>? = null
) {

  protected val map: MutableMap<K, ReferenceList<T>?> = HashMap()

  private var fullnessListenerHelper: ReferenceList.FullnessListener? = null
  private var fullnessCounter = 0
  private var reuse: ReferenceList<T>? = null

  init {
    fullnessListenerHelper = if (fullnessListener != null) {
      ReferenceList.FullnessListener { _: ReferenceList<*>?, isFull: Boolean ->
        synchronized(fullnessListener) {
          if (isFull) {
            if (fullnessCounter++ == 0) {
              fullnessListener.onFullnessStateChanged(this@ReferenceMap, true)
            }
          } else {
            if (--fullnessCounter == 0) {
              fullnessListener.onFullnessStateChanged(this@ReferenceMap, false)
            }
          }
        }
      }
    } else null
  }

  fun add(key: K, item: T): Boolean {
    synchronized(map) {
      var list = map[key]
      if (list == null) {
        if (reuse != null) {
          list = reuse
          reuse = list!!.next
          list.next = null
        } else {
          list = ReferenceList(isThreadSafe, cacheIterator, fullnessListenerHelper)
        }
        map[key] = list
      }
      return list.add(item)
    }
  }

  fun has(key: K): Boolean {
    synchronized(map) {
      val list = map[key]
      return list != null && !list.isEmpty
    }
  }

  fun remove(key: K, item: T) {
    synchronized(map) {
      val list = map[key]
      if (list != null) {
        list.remove(item)
        if (list.isEmpty) {
          map.remove(key)
          list.next = reuse
          reuse = list
        }
      }
    }
  }

  fun move(oldKey: K, newKey: K) {
    synchronized(map) {
      val oldList = map.remove(oldKey) ?: return
      val newList = map[newKey]
      if (newList != null) {
        newList.addAll(oldList)
        oldList.clear()
        oldList.next = reuse
        reuse = oldList
      } else {
        map.put(newKey, oldList)
      }
    }
  }

  fun clear() {
    synchronized(map) {
      for ((_, list) in map) {
        list!!.clear()
        list.next = reuse
        reuse = list
      }
      map.clear()
    }
  }

  fun iterator(key: K): Iterator<T>? {
    synchronized(map) {
      val list = map[key]
      return list?.iterator()
    }
  }

  /**
   * Requires prior synchronization on ReferenceMap instance
   */
  fun keySetUnchecked(): Set<K>? {
    return if (map.isEmpty()) null else map.keys
  }

  fun mapUnchecked(): Any {
    return map
  }

  fun interface FullnessListener<KK, TT> {
    fun onFullnessStateChanged(list: ReferenceMap<KK, TT>?, isFull: Boolean)
  }
}