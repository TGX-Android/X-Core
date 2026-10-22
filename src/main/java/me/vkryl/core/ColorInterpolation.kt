/*
 * This file is a part of X-Core
 * Copyright © Vyacheslav Krylov 2014
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

@file:MadeByLLM("opus-5.5")

package me.vkryl.core

import android.graphics.Color
import me.vkryl.annotation.MadeByLLM
import kotlin.math.cbrt
import kotlin.math.pow
import kotlin.math.roundToInt

/** Space in which [ColorInterpolator] interpolates colors */
enum class ColorInterpolation {
  /** Channels as they are stored, the same way Android blends colors with alpha. Same as [fromToArgb] */
  SRGB,
  /** Linear light, like [android.animation.ArgbEvaluator] since Android 8.0 */
  LINEAR,
  /** Perceptually uniform Oklab, like Jetpack Compose. Keeps transitions between hues bright */
  OKLAB
}

/**
 * Interpolates colors in [interpolation] space, with alpha premultiplied like in [fromToArgb].
 * Colors are converted to that space only when they change, e.g. once per animation, so it's not thread-safe.
 */
class ColorInterpolator (val interpolation: ColorInterpolation) {
  private var fromArgb = 0
  private var toArgb = 0

  private var fromX = 0f
  private var fromY = 0f
  private var fromZ = 0f
  private var toX = 0f
  private var toY = 0f
  private var toZ = 0f

  fun interpolate (fromArgb: Int, toArgb: Int, factor: Float): Int {
    require(!factor.isNaN())
    return when {
      fromArgb == toArgb -> toArgb
      factor <= 0f -> fromArgb
      factor >= 1f -> toArgb
      interpolation == ColorInterpolation.SRGB -> fromToArgb(fromArgb, toArgb, factor)
      else -> {
        if (fromArgb != this.fromArgb || toArgb != this.toArgb) {
          setColors(fromArgb, toArgb)
        }
        val fromAlpha = Color.alpha(fromArgb)
        val toAlpha = Color.alpha(toArgb)
        val alpha = fromTo(fromAlpha.toFloat(), toAlpha.toFloat(), factor)
        val toWeight = if (fromAlpha == toAlpha) factor else toAlpha * factor / alpha
        val fromWeight = 1f - toWeight
        val x = fromX * fromWeight + toX * toWeight
        val y = fromY * fromWeight + toY * toWeight
        val z = fromZ * fromWeight + toZ * toWeight
        if (interpolation == ColorInterpolation.LINEAR) {
          Color.argb(alpha.roundToInt(), linearToSrgb(x), linearToSrgb(y), linearToSrgb(z))
        } else {
          oklabToArgb(alpha.roundToInt(), x, y, z)
        }
      }
    }
  }

  private fun setColors (fromArgb: Int, toArgb: Int) {
    this.fromArgb = fromArgb
    this.toArgb = toArgb
    if (interpolation == ColorInterpolation.LINEAR) {
      fromX = srgbToLinear(Color.red(fromArgb))
      fromY = srgbToLinear(Color.green(fromArgb))
      fromZ = srgbToLinear(Color.blue(fromArgb))
      toX = srgbToLinear(Color.red(toArgb))
      toY = srgbToLinear(Color.green(toArgb))
      toZ = srgbToLinear(Color.blue(toArgb))
    } else {
      toOklab(fromArgb) { lightness, a, b ->
        fromX = lightness
        fromY = a
        fromZ = b
      }
      toOklab(toArgb) { lightness, a, b ->
        toX = lightness
        toY = a
        toZ = b
      }
    }
  }
}

// sRGB transfer function: https://www.w3.org/TR/css-color-4/#color-conversion-code
private fun srgbToLinear (channel: Int): Float {
  val srgb = channel / 255f
  return if (srgb <= 0.04045f) srgb / 12.92f else ((srgb + 0.055f) / 1.055f).pow(2.4f)
}

private fun linearToSrgb (value: Float): Int {
  val linear = value.coerceIn(0f, 1f)
  val srgb = if (linear <= 0.0031308f) linear * 12.92f else 1.055f * linear.pow(1f / 2.4f) - 0.055f
  return (srgb * 255f).roundToInt()
}

// Oklab conversion with coefficients rounded to float: https://bottosson.github.io/posts/oklab/
private inline fun toOklab (argb: Int, block: (lightness: Float, a: Float, b: Float) -> Unit) {
  val red = srgbToLinear(Color.red(argb))
  val green = srgbToLinear(Color.green(argb))
  val blue = srgbToLinear(Color.blue(argb))
  val l = cbrt(0.41222146f * red + 0.53633255f * green + 0.051445995f * blue)
  val m = cbrt(0.2119035f * red + 0.6806995f * green + 0.10739696f * blue)
  val s = cbrt(0.08830246f * red + 0.28171885f * green + 0.6299787f * blue)
  block(
    0.21045426f * l + 0.7936178f * m - 0.004072047f * s,
    1.9779985f * l - 2.4285922f * m + 0.4505937f * s,
    0.025904037f * l + 0.78277177f * m - 0.80867577f * s
  )
}

private fun oklabToArgb (alpha: Int, lightness: Float, a: Float, b: Float): Int {
  val l = cube(lightness + 0.39633778f * a + 0.21580376f * b)
  val m = cube(lightness - 0.105561346f * a - 0.06385417f * b)
  val s = cube(lightness - 0.08948418f * a - 1.2914855f * b)
  return Color.argb(
    alpha,
    linearToSrgb(4.0767417f * l - 3.3077116f * m + 0.23096994f * s),
    linearToSrgb(-1.268438f * l + 2.6097574f * m - 0.34131938f * s),
    linearToSrgb(-0.0041960864f * l - 0.7034186f * m + 1.7076147f * s)
  )
}

private fun cube (value: Float): Float = value * value * value