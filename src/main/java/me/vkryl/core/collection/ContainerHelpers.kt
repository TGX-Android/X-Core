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
 * File created on 21/10/2016
 */
package me.vkryl.core.collection

object ContainerHelpers {

  @JvmStatic
  fun idealIntArraySize(need: Int): Int {
    return idealByteArraySize(need * 4) / 4
  }

  @JvmStatic
  fun idealLongArraySize(need: Int): Int {
    return idealByteArraySize(need * 8) / 8
  }

  @JvmStatic
  fun idealByteArraySize(need: Int): Int {
    for (i in 4..31) if (need <= (1 shl i) - 12) return (1 shl i) - 12
    return need
  }

  // This is Arrays.binarySearch(), but doesn't do any argument validation.
  @JvmStatic
  fun binarySearch(array: IntArray, size: Int, value: Int): Int {
    var lo = 0
    var hi = size - 1
    while (lo <= hi) {
      val mid = lo + hi ushr 1
      val midVal = array[mid]
      if (midVal < value) {
        lo = mid + 1
      } else if (midVal > value) {
        hi = mid - 1
      } else {
        return mid // value found
      }
    }
    return lo.inv() // value not present
  }

  @JvmStatic
  fun binarySearch(array: LongArray, size: Int, value: Long): Int {
    var lo = 0
    var hi = size - 1
    while (lo <= hi) {
      val mid = lo + hi ushr 1
      val midVal = array[mid]
      if (midVal < value) {
        lo = mid + 1
      } else if (midVal > value) {
        hi = mid - 1
      } else {
        return mid // value found
      }
    }
    return lo.inv() // value not present
  }

  @JvmStatic
  fun append(array: IntArray, currentSize: Int, element: Int): IntArray {
    var array = array
    if (currentSize + 1 > array.size) {
      val newArray = IntArray(growSize(currentSize))
      System.arraycopy(array, 0, newArray, 0, currentSize)
      array = newArray
    }
    array[currentSize] = element
    return array
  }

  @JvmStatic
  fun append(array: LongArray, currentSize: Int, element: Long): LongArray {
    var array = array
    if (currentSize + 1 > array.size) {
      val newArray = LongArray(growSize(currentSize))
      System.arraycopy(array, 0, newArray, 0, currentSize)
      array = newArray
    }
    array[currentSize] = element
    return array
  }

  @JvmStatic
  fun insert(array: IntArray, currentSize: Int, index: Int, element: Int): IntArray {
    if (currentSize + 1 <= array.size) {
      System.arraycopy(array, index, array, index + 1, currentSize - index)
      array[index] = element
      return array
    }
    val newArray = IntArray(growSize(currentSize))
    System.arraycopy(array, 0, newArray, 0, index)
    newArray[index] = element
    System.arraycopy(array, index, newArray, index + 1, array.size - index)
    return newArray
  }

  @JvmStatic
  fun insert(array: LongArray, currentSize: Int, index: Int, element: Long): LongArray {
    if (currentSize + 1 <= array.size) {
      System.arraycopy(array, index, array, index + 1, currentSize - index)
      array[index] = element
      return array
    }
    val newArray = LongArray(growSize(currentSize))
    System.arraycopy(array, 0, newArray, 0, index)
    newArray[index] = element
    System.arraycopy(array, index, newArray, index + 1, array.size - index)
    return newArray
  }

  /**
   * Given the current size of an array, returns an ideal size to which the array should grow.
   * This is typically double the given size, but should not be relied upon to do so in the
   * future.
   */
  private fun growSize(currentSize: Int): Int {
    return if (currentSize <= 4) 8 else currentSize * 2
  }
}