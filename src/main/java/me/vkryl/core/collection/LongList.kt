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
 * File created on 28/11/2016
 */
package me.vkryl.core.collection

import me.vkryl.core.toString
import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder
import kotlin.math.max

class LongList {

  val isEmpty: Boolean
    get() = size == 0

  private var data: LongArray
  private var size = 0

  constructor(initialCapacity: Int) {
    data = LongArray(initialCapacity)
  }

  constructor(source: LongArray) {
    data = source
    size = source.size
  }

  override fun toString(): String {
    return toString(data, size)
  }

  fun ensureCapacity(x: Int, increaseCount: Int) {
    if (data.size < x) {
      val dest = LongArray(max(x, data.size + increaseCount))
      System.arraycopy(data, 0, dest, 0, data.size)
      data = dest
    }
  }

  fun clear() {
    size = 0
  }

  fun append(x: Long) {
    ensureCapacity(size + 1, 10)
    data[size++] = x
  }

  fun appendAll(x: LongArray) {
    if (x.isNotEmpty()) {
      ensureCapacity(size + x.size, 10)
      System.arraycopy(x, 0, data, size, x.size)
      size += x.size
    }
  }

  fun appendAll(list: LongList) {
    if (list.size > 0) {
      ensureCapacity(size + list.size, 0)
      System.arraycopy(list.data, 0, data, size, list.size)
      size += list.size
    }
  }

  fun get(): LongArray {
    trim()
    return data
  }

  fun size(): Int {
    return size
  }

  fun last(): Long {
    return data[size - 1]
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

  fun remove(value: Long): Boolean {
    val i = indexOf(value)
    if (i == -1) {
      return false
    }
    removeAt(i)
    return true
  }

  fun indexOf(value: Long): Int {
    for (i in 0 until size) {
      if (data[i] == value) {
        return i
      }
    }
    return -1
  }

  fun toStringBuilder(b: StringBuilder) {
    var first = true
    var i = 0
    while (i++ < size) {
      if (first) {
        first = false
      } else {
        b.append(',')
      }
      b.append(data[i])
    }
  }

  operator fun get(i: Int): Long {
    return data[i]
  }

  operator fun contains(x: Long): Boolean {
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
      val dest = LongArray(size)
      System.arraycopy(data, 0, dest, 0, size)
      data = dest
    }
  }
}