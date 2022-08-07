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
 * File created on 19/12/2017
 */
package me.vkryl.core.util

import android.os.Looper
import java.util.HashMap

class LocalVar<T> {

  var main: T? = null
    private set

  private var looperMap: HashMap<Looper, T>? = null
  private var threadMap: HashMap<Thread, T>? = null

  fun setMain(`var`: T) {
    main = `var`
  }

  fun get(): T? {
    val looper = Looper.myLooper()
    return if (looper == Looper.getMainLooper()) {
      main
    } else if (looper != null) {
      var result: T?
      synchronized(this) {
        result = if (looperMap != null)
          looperMap!![looper]
        else null
      }
      result
    } else {
      val thread = Thread.currentThread()
      var result: T?
      synchronized(this) {
        result = if (threadMap != null)
          threadMap!![thread]
        else null
      }
      result
    }
  }

  fun set(value: T) {
    val looper = Looper.myLooper()
    if (looper == Looper.getMainLooper()) {
      main = value
    } else if (value == null) {
      if (looper != null) {
        synchronized(this) {
          if (looperMap != null) {
            looperMap?.remove(looper)
          }
        }
      } else {
        val thread = Thread.currentThread()
        synchronized(this) {
          if (threadMap != null) {
            threadMap?.remove(thread)
          }
        }
      }
    } else {
      if (looper != null) {
        synchronized(this) {
          if (looperMap == null) {
            looperMap = HashMap()
          }
          looperMap?.put(looper, value)
        }
      } else {
        val thread = Thread.currentThread()
        synchronized(this) {
          if (threadMap == null) {
            threadMap = HashMap()
          }
          threadMap?.put(thread, value)
        }
      }
    }
  }
}