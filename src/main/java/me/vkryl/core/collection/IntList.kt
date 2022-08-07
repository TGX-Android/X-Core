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
 * File created on 07/02/2016 at 21:25
 */
package me.vkryl.core.collection

import java.lang.IndexOutOfBoundsException
import kotlin.math.max

class IntList {

  val isEmpty: Boolean
    get() = size == 0

  private var data: IntArray
  private var size = 0

  constructor(initialCapacity: Int) {
    data = IntArray(initialCapacity)
  }

  constructor(source: IntArray) {
    data = source
    size = source.size
  }

  fun ensureCapacity(x: Int, increaseCount: Int) {
    if (data.size < x) {
      val dest = IntArray(max(x, data.size + increaseCount))
      System.arraycopy(data, 0, dest, 0, data.size)
      data = dest
    }
  }

  fun clear() {
    size = 0
  }

  fun append(x: Int) {
    ensureCapacity(size + 1, 10)
    data[size++] = x
  }

  fun appendAll(x: IntArray) {
    ensureCapacity(size + x.size, 10)
    System.arraycopy(x, 0, data, size, x.size)
    size += x.size
  }

  fun appendAll(list: IntList) {
    if (list.size > 0) {
      ensureCapacity(size + list.size, 0)
      list.trim()
      System.arraycopy(list.data, 0, data, size, list.data.size)
      size += list.size
    }
  }

  fun get(): IntArray {
    trim()
    return data
  }

  fun remove(value: Int): Boolean {
    val i = indexOf(value)
    if (i == -1) {
      return false
    }
    removeAt(i)
    return true
  }

  fun removeAt(i: Int) {
    if (i < 0 || i >= size) {
      throw IndexOutOfBoundsException()
    }
    if (i + 1 < size) {
      System.arraycopy(data, i + 1, data, i, size - i - 1)
    }
    size--
  }

  fun indexOf(value: Int): Int {
    for (i in 0 until size) {
      if (data[i] == value) {
        return i
      }
    }
    return -1
  }

  fun removeLast(): Int {
    return data[--size]
  }

  fun size(): Int {
    return size
  }

  fun last(): Int {
    return data[size - 1]
  }

  operator fun get(i: Int): Int {
    return data[i]
  }

  operator fun set(i: Int, value: Int) {
    data[i] = value
  }

  operator fun contains(x: Int): Boolean {
    for ((i, cx) in data.withIndex()) {
      if (i == size) {
        break
      }
      if (cx == x) {
        return true
      }
    }
    return false
  }

  private fun trim() {
    if (size < data.size) {
      val dest = IntArray(size)
      System.arraycopy(data, 0, dest, 0, size)
      data = dest
    }
  }
}