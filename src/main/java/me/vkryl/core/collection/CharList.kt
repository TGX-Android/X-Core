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
 * File created on 13/03/2016 at 20:54
 */
package me.vkryl.core.collection

import kotlin.math.max

class CharList(initialCapacity: Int) {

  private var data = CharArray(initialCapacity)
  private var size = 0

  fun ensureCapacity(x: Int, increaseCount: Int) {
    if (data.size < x) {
      val dest = CharArray(max(x, data.size + increaseCount))
      System.arraycopy(data, 0, dest, 0, data.size)
      data = dest
    }
  }

  fun trim() {
    if (size < data.size) {
      val dest = CharArray(size)
      System.arraycopy(data, 0, dest, 0, size)
      data = dest
    }
  }

  fun clear() {
    size = 0
  }

  fun append(x: Char) {
    ensureCapacity(size + 1, 10)
    data[size++] = x
  }

  fun appendAll(list: CharList) {
    if (list.size > 0) {
      ensureCapacity(size + list.size, 0)
      list.trim()
      System.arraycopy(list.data, 0, data, size, list.data.size)
      size += list.size
    }
  }

  fun get(): CharArray {
    return data
  }

  fun size(): Int {
    return size
  }

  fun last(): Char {
    return data[size - 1]
  }

  operator fun get(i: Int): Char {
    return data[i]
  }
}