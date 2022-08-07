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
 * File created on 25/02/2017
 */
package me.vkryl.core.collection

import kotlin.math.max

class FloatList {

  val isEmpty: Boolean
    get() = size == 0

  private var data: FloatArray
  private var size = 0

  constructor(initialCapacity: Int) {
    data = FloatArray(initialCapacity)
  }

  constructor(source: FloatArray) {
    data = source
    size = source.size
  }

  fun ensureCapacity(x: Int, increaseCount: Int) {
    if (data.size < x) {
      val dest = FloatArray(max(x, data.size + increaseCount))
      System.arraycopy(data, 0, dest, 0, data.size)
      data = dest
    }
  }

  fun trim() {
    if (size < data.size) {
      val dest = FloatArray(size)
      System.arraycopy(data, 0, dest, 0, size)
      data = dest
    }
  }

  fun clear() {
    size = 0
  }

  fun append(x: Float) {
    ensureCapacity(size + 1, if (data.isEmpty()) 10 else data.size shl 1)
    data[size++] = x
  }

  fun appendAll(x: FloatArray) {
    ensureCapacity(size + x.size, 10)
    System.arraycopy(x, 0, data, size, x.size)
    size += x.size
  }

  fun appendAll(list: FloatList) {
    if (list.size > 0) {
      ensureCapacity(size + list.size, 0)
      list.trim()
      System.arraycopy(list.data, 0, data, size, list.data.size)
      size += list.size
    }
  }

  fun sum(): Float {
    var x = 0f
    for (i in 0 until size) {
      x += data[i]
    }
    return x
  }

  fun get(): FloatArray {
    trim()
    return data
  }

  fun size(): Int {
    return size
  }

  fun last(): Float {
    return data[size - 1]
  }

  operator fun get(i: Int): Float {
    return data[i]
  }

  operator fun contains(x: Float): Boolean {
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
}