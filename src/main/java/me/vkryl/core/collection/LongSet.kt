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
 * File created on 15/01/2021
 */
package me.vkryl.core.collection

import java.util.*

class LongSet : Iterable<Long> {

  private val sortedList: ArrayList<Long>

  constructor() {
    sortedList = ArrayList()
  }

  constructor(initialCapacity: Int) {
    sortedList = ArrayList(initialCapacity)
  }

  fun ensureCapacity(minCapacity: Int) {
    sortedList.ensureCapacity(minCapacity)
  }

  fun addAll(vararg items: Long) {
    for (item in items) {
      add(item)
    }
  }

  fun addAll(items: LongSet) {
    ensureCapacity(size() + items.size())
    for (item in items) {
      add(item)
    }
  }

  fun add(item: Long): Boolean {
    var position = Collections.binarySearch(sortedList, item)
    if (position >= 0) {
      return false
    }
    position = -position - 1
    sortedList.add(position, item)
    return true
  }

  fun remove(item: Long): Boolean {
    val position = Collections.binarySearch(sortedList, item)
    if (position < 0) {
      return false
    }
    sortedList.removeAt(position)
    return true
  }

  fun replace(item: Long, withItem: Long): Boolean {
    if (remove(item)) {
      add(withItem)
      return true
    }
    return false
  }

  fun clear() {
    sortedList.clear()
  }

  fun has(item: Long): Boolean {
    return Collections.binarySearch(sortedList, item) >= 0
  }

  fun size(): Int {
    return sortedList.size
  }

  fun toArray(): LongArray {
    val array = LongArray(sortedList.size)
    for (i in sortedList.indices) {
      array[i] = sortedList[i]
    }
    return array
  }

  override fun iterator(): MutableIterator<Long> {
    return sortedList.iterator()
  }
}