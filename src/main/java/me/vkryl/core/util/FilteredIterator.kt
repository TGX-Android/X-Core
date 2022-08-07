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
 * File created on 07/01/2019
 */
package me.vkryl.core.util

import me.vkryl.core.lambda.Filter

class FilteredIterator<T>(
  private val itr: Iterator<T>?,
  private val filter: Filter<T>,
) : Iterator<T>, Iterable<T> {

  private var next: T? = null

  constructor(itr: Iterable<T>?, filter: Filter<T>) : this(itr?.iterator(), filter)

  override fun iterator(): Iterator<T> {
    return this
  }

  override fun hasNext(): Boolean {
    if (itr == null) return false
    next = null
    do {
      val hasNext = itr.hasNext()
      if (!hasNext) return false
      val next = itr.next()
      if (!filter.accept(next)) continue
      this.next = next
      return true
    } while (true)
  }

  override fun next(): T {
    return next!!
  }
}