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
 * File created on 25/03/2019
 */
package me.vkryl.core.util

import me.vkryl.core.lambda.FutureBool

class JobList(private val canExecute: FutureBool) {

  val isEmpty: Boolean
    get() = synchronized(list) {
      return list.isEmpty()
    }

  private val list = ArrayList<Runnable>()

  @Volatile
  private var value = false
  private var onAdd: Runnable? = null
  private var onRemove: Runnable? = null

  init {
    synchronized(list) {
      value = canExecute.get()
    }
  }

  fun onAddRemove(onAdd: Runnable, onRemove: Runnable): JobList {
    this.onAdd = onAdd
    this.onRemove = onRemove
    return this
  }

  private fun value(): Boolean {
    val newValue = canExecute.get()
    if (value != newValue) {
      value = newValue
      return newValue
    }
    check(!(newValue && list.isNotEmpty()))
    return newValue
  }

  fun add(runnable: Runnable) {
    var removedCount = 0
    var added = false
    synchronized(list) {
      if (value()) {
        runnable.run()
        removedCount = execute()
      } else {
        list.add(runnable)
        added = true
      }
    }
    if (added) {
      if (onAdd != null) onAdd?.run()
    } else if (onRemove != null) {
      for (i in 0 until removedCount)
        onRemove?.run()
    }
  }

  @JvmOverloads
  fun trigger(force: Boolean = false) {
    var removedCount: Int
    synchronized(list) {
      removedCount = if (value() || force) execute() else 0
    }
    if (onRemove != null) {
      for (i in 0 until removedCount) onRemove?.run()
    }
  }

  private fun execute(): Int {
    val count = list.size
    if (count == 0) return 0
    val pending = ArrayList(list)
    list.clear()
    for (i in count - 1 downTo 0) {
      pending[i].run()
    }
    return count
  }
}