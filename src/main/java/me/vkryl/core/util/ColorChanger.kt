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
 * File created on 06/08/2015 at 16:36
 */
package me.vkryl.core.util

import android.graphics.Color
import androidx.annotation.ColorInt

class ColorChanger(
  @ColorInt private var fromColor: Int,
  @ColorInt private var toColor: Int,
) {

  private var fromA = Color.alpha(fromColor)
  private var fromR = Color.red(fromColor)
  private var fromG = Color.green(fromColor)
  private var fromB = Color.blue(fromColor)
  private var aDiff = Color.alpha(toColor) - fromA
  private var rDiff = Color.red(toColor) - fromR
  private var gDiff = Color.green(toColor) - fromG
  private var bDiff = Color.blue(toColor) - fromB

  private var canOverflow = false

  fun setCanOverflow() {
    canOverflow = true
  }

  fun setFromTo(@ColorInt fromColor: Int, @ColorInt toColor: Int) {
    this.fromColor = fromColor
    this.toColor = toColor
    fromA = Color.alpha(fromColor)
    fromR = Color.red(fromColor)
    fromG = Color.green(fromColor)
    fromB = Color.blue(fromColor)
    aDiff = Color.alpha(toColor) - fromA
    rDiff = Color.red(toColor) - fromR
    gDiff = Color.green(toColor) - fromG
    bDiff = Color.blue(toColor) - fromB
  }

  var from: Int
    @ColorInt
    get() = fromColor
    set(fromColor) {
      if (this.fromColor != fromColor) {
        this.fromColor = fromColor
        fromA = Color.alpha(fromColor)
        fromR = Color.red(fromColor)
        fromG = Color.green(fromColor)
        fromB = Color.blue(fromColor)
        aDiff = Color.alpha(toColor) - fromA
        rDiff = Color.red(toColor) - fromR
        gDiff = Color.green(toColor) - fromG
        bDiff = Color.blue(toColor) - fromB
      }
    }

  var to: Int
    @ColorInt
    get() = toColor
    set(toColor) {
      if (this.toColor != toColor) {
        this.toColor = toColor
        aDiff = Color.alpha(toColor) - fromA
        rDiff = Color.red(toColor) - fromR
        gDiff = Color.green(toColor) - fromG
        bDiff = Color.blue(toColor) - fromB
      }
    }

  @ColorInt
  fun getColor(x: Float): Int {
    return if (x <= 0f) fromColor else if (x >= 1f && !canOverflow) toColor else Color.argb(
      fromA + (aDiff * x).toInt(),
      fromR + (rDiff * x).toInt(),
      fromG + (gDiff * x).toInt(),
      fromB + (bDiff * x).toInt()
    )
  }
}