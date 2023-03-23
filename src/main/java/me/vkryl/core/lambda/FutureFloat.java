/*
 * This file is a part of X-Core
 * Copyright © Vyacheslav Krylov (slavone@protonmail.ch) 2014
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
 * File created on 23/03/2023
 */
package me.vkryl.core.lambda;

import me.vkryl.core.MathUtils;

public interface FutureFloat {
  float getFloatValue ();

  default float interpolate (float toValue, float factor) {
    return MathUtils.fromTo(getFloatValue(), toValue, factor);
  }
}