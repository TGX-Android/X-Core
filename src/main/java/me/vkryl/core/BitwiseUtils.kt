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
 */
package me.vkryl.core

object BitwiseUtils {

  @JvmStatic
  fun getFlag(flags: Int, flag: Int): Boolean {
    return flags and flag != 0
  }

  @JvmStatic
  fun getFlag(flags: Long, flag: Long): Boolean {
    return flags and flag != 0L
  }

  @JvmStatic
  fun setFlag(flags: Int, flag: Int, enabled: Boolean): Int {
    var flags = flags
    flags = if (enabled) {
      flags or flag
    } else {
      flags and flag.inv()
    }
    return flags
  }

  @JvmStatic
  fun setFlag(flags: Long, flag: Long, enabled: Boolean): Long {
    var flags = flags
    flags = if (enabled) {
      flags or flag
    } else {
      flags and flag.inv()
    }
    return flags
  }

  @JvmStatic
  fun hashCode(x: Long): Int {
    return (x xor (x ushr 32)).toInt()
  }

  @JvmStatic
  fun splitLongToFirstInt(x: Long): Int {
    return (x shr 32).toInt()
  }

  @JvmStatic
  fun splitLongToSecondInt(x: Long): Int {
    return x.toInt()
  }

  @JvmStatic
  fun mergeLong(a: Int, b: Int): Long {
    return (a.toLong() shl 32) or (b.toLong() and 0xFFFFFFFFL)
  }

  @JvmStatic
  fun mergeTimeToInt(hour: Int, minute: Int, second: Int): Int {
    return 0xff and hour shl 16 or (0xff and minute shl 8) or (0xff and second)
  }

  @JvmStatic
  fun splitIntToSecond(time: Int): Int {
    return time and 0xff
  }

  @JvmStatic
  fun splitIntToMinute(time: Int): Int {
    return time shr 8 and 0xff
  }

  @JvmStatic
  fun splitIntToHour(time: Int): Int {
    return time shr 16 and 0xff
  }

  @JvmStatic
  fun belongsToSchedule(time: Int, startTime: Int, endTime: Int): Boolean {
    if (startTime == endTime) {
      return false
    }
    val startHour = splitIntToHour(startTime)
    val startMinute = splitIntToMinute(startTime)
    val startSecond = splitIntToSecond(startTime)
    val endHour = splitIntToHour(endTime)
    val endMinute = splitIntToMinute(endTime)
    val endSecond = splitIntToSecond(endTime)
    val hour = splitIntToHour(time)
    val minute = splitIntToMinute(time)
    val second = splitIntToSecond(time)
    if (hour == startHour && minute == startMinute && second == startSecond) {
      return true
    }
    if (hour == endHour && minute == endMinute && second == endSecond) {
      return false
    }

    //  isAfter(startHour, startMinute, startSecond, endHour, endMinute, endSecond)
    return if (isAfter(startHour, endHour)) {
      // 22:00-end || start-7:00
      // return isAfter(hour, minute, second, startHour, startMinute, startSecond) || isAfter(endHour, endMinute, endSecond, hour, minute, second);
      isAfter(time, startTime) || isAfter(endTime, time)
    } else {
      // 7:00-22:00
      // return isAfter(hour, minute, second, startHour, startMinute, startSecond) && isAfter(endHour, endMinute, endSecond, hour, minute, second);
      isAfter(time, startTime) && isAfter(endTime, time)
    }
  }

  private fun isAfter(time: Int, afterTime: Int): Boolean {
    return time > afterTime
  }
}