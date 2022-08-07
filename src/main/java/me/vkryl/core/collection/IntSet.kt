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

class IntSet : Iterable<Int> {

  private val sortedList: ArrayList<Int>

  constructor() {
    sortedList = ArrayList()
  }

  constructor(initialCapacity: Int) {
    sortedList = ArrayList(initialCapacity)
  }

  fun ensureCapacity(minCapacity: Int) {
    sortedList.ensureCapacity(minCapacity)
  }

  fun addAll(vararg items: Int) {
    for (item in items) {
      add(item)
    }
  }

  fun add(item: Int): Boolean {
    var position = Collections.binarySearch(sortedList, item)
    if (position >= 0) {
      return false
    }
    position = -position - 1
    sortedList.add(position, item)
    return true
  }

  fun remove(item: Int): Boolean {
    val position = Collections.binarySearch(sortedList, item)
    if (position < 0) {
      return false
    }
    sortedList.removeAt(position)
    return true
  }

  fun clear() {
    sortedList.clear()
  }

  fun has(item: Int): Boolean {
    return Collections.binarySearch(sortedList, item) >= 0
  }

  fun size(): Int {
    return sortedList.size
  }

  override fun iterator(): MutableIterator<Int> {
    return sortedList.iterator()
  }
}