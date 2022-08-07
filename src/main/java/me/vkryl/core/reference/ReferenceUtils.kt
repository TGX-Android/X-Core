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

import androidx.collection.LongSparseArray
import androidx.collection.SparseArrayCompat
import java.lang.ref.Reference
import java.lang.ref.WeakReference

object ReferenceUtils {

  @JvmStatic
  fun <T> removeReference(
    list: MutableList<Reference<T>>,
    data: T,
  ): Boolean {
    val size = list.size
    for (i in size - 1 downTo 0) {
      val oldData = list[i].get()
      if (oldData == null || oldData === data) {
        list.removeAt(i)
        return true
      }
    }
    return false
  }

  @JvmStatic
  fun <T> removeReference(
    array: LongSparseArray<MutableList<Reference<T>>?>,
    data: T,
    key: Long
  ): Boolean {
    val list = array[key]
    if (list != null) {
      val removed = removeReference(list, data)
      if (list.isEmpty()) {
        array.remove(key)
      }
      return removed
    }
    return false
  }

  @JvmStatic
  fun <K, T> removeReference(
    array: HashMap<K, MutableList<Reference<T>>?>,
    data: T,
    key: K,
  ): Boolean {
    val list = array[key]
    if (list != null) {
      val removed = removeReference(list, data)
      if (list.isEmpty()) {
        array.remove(key)
      }
      return removed
    }
    return false
  }

  @JvmStatic
  fun <T> removeReference(
    array: SparseArrayCompat<MutableList<Reference<T>>?>,
    data: T,
    key: Int
  ): Boolean {
    val list = array[key]
    if (list != null) {
      val removed = removeReference(list, data)
      if (list.isEmpty()) {
        array.remove(key)
      }
      return removed
    }
    return false
  }

  @JvmStatic
  fun <T> addReference(
    array: SparseArrayCompat<MutableList<Reference<T>?>?>,
    data: T,
    key: Int
  ): Boolean {
    val list = array[key]
    return if (list != null) {
      addReference(list, data)
    } else {
      val newList: MutableList<Reference<T>?> =
        ArrayList()
      newList.add(WeakReference(data))
      array.put(key, newList)
      true
    }
  }

  @JvmStatic
  fun <T> addReference(
    list: MutableList<Reference<T>?>,
    item: T,
  ): Boolean {
    var found = false
    val size = list.size
    for (i in size - 1 downTo 0) {
      val reference = list[i]
      val oldItem = reference?.get()
      if (oldItem == null) {
        list.removeAt(i)
      } else if (oldItem === item) {
        found = true
      }
    }
    if (!found) {
      list.add(WeakReference(item))
      return true
    }
    return false
  }

  @JvmStatic
  fun <T> addReference(
    creator: ReferenceCreator<T>,
    list: MutableList<Reference<T>?>,
    item: T
  ): Boolean {
    var found = false
    val size = list.size
    for (i in size - 1 downTo 0) {
      val reference = list[i]
      val oldItem = reference?.get()
      if (oldItem == null) {
        list.removeAt(i)
      } else if (oldItem === item) {
        found = true
      }
    }
    if (!found) {
      val reference = creator.newReference(item)
      list.add(reference)
      return true
    }
    return false
  }

  @JvmStatic
  fun <T> gcReferenceList(
    list: MutableList<Reference<T>>?,
  ) {
    if (list != null) {
      val size = list.size - 1
      for (i in size - 1 downTo 0) {
        val data = list[i].get()
        if (data == null) {
          list.removeAt(i)
        }
      }
    }
  }

  @JvmStatic
  fun <T> removeListIfEmpty(
    array: LongSparseArray<List<Reference<T>?>?>?,
    list: List<Reference<T>?>?,
    key: Long
  ) {
    if (array != null && list != null && list.isEmpty()) {
      array.remove(key)
    }
  }

  @JvmStatic
  fun <T> removeListIfEmpty(
    array: SparseArrayCompat<List<Reference<T>?>?>?,
    list: List<Reference<T>?>?,
    key: Int
  ) {
    if (array != null && list != null && list.isEmpty()) {
      array.remove(key)
    }
  }
}