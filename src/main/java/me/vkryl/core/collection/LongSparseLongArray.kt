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
 * File created on 13/12/2016
 */
package me.vkryl.core.collection

import me.vkryl.core.EMPTY_LONGS
import me.vkryl.core.collection.ContainerHelpers.append
import me.vkryl.core.collection.ContainerHelpers.binarySearch
import me.vkryl.core.collection.ContainerHelpers.idealLongArraySize
import me.vkryl.core.collection.ContainerHelpers.insert

class LongSparseLongArray @JvmOverloads constructor(initialCapacity: Int = 10) : Cloneable {

  private var mKeys: LongArray
  private var mValues: LongArray
  private var mSize: Int

  /**
   * Creates a new SparseIntArray containing no mappings that will not
   * require any additional memory allocation to store the specified
   * number of mappings.  If you supply an initial capacity of 0, the
   * sparse array will be initialized with a light-weight representation
   * not requiring any additional array allocations.
   */
  /**
   * Creates a new SparseIntArray containing no mappings.
   */
  init {
    var initialCapacity = initialCapacity
    if (initialCapacity == 0) {
      mKeys = EMPTY_LONGS
      mValues = EMPTY_LONGS
    } else {
      initialCapacity = idealLongArraySize(initialCapacity)
      mKeys = LongArray(initialCapacity)
      mValues = LongArray(initialCapacity)
    }
    mSize = 0
  }

  public override fun clone(): LongSparseLongArray {
    var clone: LongSparseLongArray? = null
    try {
      clone = super.clone() as LongSparseLongArray
      clone.mKeys = mKeys.clone()
      clone.mValues = mValues.clone()
    } catch (cnse: CloneNotSupportedException) {
      /* ignore */
    }
    return clone!!
  }

  /**
   * Gets the int mapped from the specified key, or the specified value
   * if no such mapping has been made.
   */
  /**
   * Gets the int mapped from the specified key, or `0`
   * if no such mapping has been made.
   */
  @JvmOverloads
  operator fun get(key: Long, valueIfKeyNotFound: Long = 0): Long {
    val i = binarySearch(mKeys, mSize, key)
    return if (i < 0) {
      valueIfKeyNotFound
    } else {
      mValues[i]
    }
  }

  /**
   * Removes the mapping from the specified key, if there was any.
   */
  fun delete(key: Long) {
    val i = binarySearch(mKeys, mSize, key)
    if (i >= 0) {
      removeAt(i)
    }
  }

  /**
   * Removes the mapping at the given index.
   */
  fun removeAt(index: Int) {
    System.arraycopy(mKeys, index + 1, mKeys, index, mSize - (index + 1))
    System.arraycopy(mValues, index + 1, mValues, index, mSize - (index + 1))
    mSize--
  }

  /**
   * Adds a mapping from the specified key to the specified value,
   * replacing the previous mapping from the specified key if there
   * was one.
   */
  fun put(key: Long, value: Long) {
    var i = binarySearch(mKeys, mSize, key)
    if (i >= 0) {
      mValues[i] = value
    } else {
      i = i.inv()
      mKeys = insert(mKeys, mSize, i, key)
      mValues = insert(mValues, mSize, i, value)
      mSize++
    }
  }

  /**
   * Returns the number of key-value mappings that this SparseIntArray
   * currently stores.
   */
  fun size(): Int {
    return mSize
  }

  /**
   * Given an index in the range `0...size()-1`, returns
   * the key from the `index`th key-value mapping that this
   * SparseIntArray stores.
   *
   *
   * The keys corresponding to indices in ascending order are guaranteed to
   * be in ascending order, e.g., `keyAt(0)` will return the
   * smallest key and `keyAt(size()-1)` will return the largest
   * key.
   */
  fun keyAt(index: Int): Long {
    return mKeys[index]
  }

  /**
   * Given an index in the range `0...size()-1`, returns
   * the value from the `index`th key-value mapping that this
   * SparseIntArray stores.
   *
   *
   * The values corresponding to indices in ascending order are guaranteed
   * to be associated with keys in ascending order, e.g.,
   * `valueAt(0)` will return the value associated with the
   * smallest key and `valueAt(size()-1)` will return the value
   * associated with the largest key.
   */
  fun valueAt(index: Int): Long {
    return mValues[index]
  }

  /**
   * Directly set the value at a particular index.
   * @hide
   */
  fun setValueAt(index: Int, value: Long) {
    mValues[index] = value
  }

  /**
   * Returns the index for which [.keyAt] would return the
   * specified key, or a negative number if the specified
   * key is not mapped.
   */
  fun indexOfKey(key: Long): Int {
    return binarySearch(mKeys, mSize, key)
  }

  /**
   * Returns an index for which [.valueAt] would return the
   * specified key, or a negative number if no keys map to the
   * specified value.
   * Beware that this is a linear search, unlike lookups by key,
   * and that multiple keys can map to the same value and this will
   * find only one of them.
   */
  fun indexOfValue(value: Long): Int {
    for (i in 0 until mSize) if (mValues[i] == value) return i
    return -1
  }

  /**
   * Removes all key-value mappings from this SparseIntArray.
   */
  fun clear() {
    mSize = 0
  }

  /**
   * Puts a key/value pair into the array, optimizing for the case where
   * the key is greater than all existing keys in the array.
   */
  fun append(key: Long, value: Long) {
    if (mSize != 0 && key <= mKeys[mSize - 1]) {
      put(key, value)
      return
    }
    mKeys = append(mKeys, mSize, key)
    mValues = append(mValues, mSize, value)
    mSize++
  }

  /**
   * Provides a copy of keys.
   *
   * @hide
   */
  fun copyKeys(): LongArray? {
    return if (size() == 0) {
      null
    } else mKeys.copyOf(size())
  }

  /**
   * {@inheritDoc}
   *
   *
   * This implementation composes a string by iterating over its mappings.
   */
  override fun toString(): String {
    if (size() <= 0) {
      return "{}"
    }
    val buffer = StringBuilder(mSize * 28)
    buffer.append('{')
    for (i in 0 until mSize) {
      if (i > 0) {
        buffer.append(", ")
      }
      val key = keyAt(i)
      buffer.append(key)
      buffer.append('=')
      val value = valueAt(i)
      buffer.append(value)
    }
    buffer.append('}')
    return buffer.toString()
  }
}