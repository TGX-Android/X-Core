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
 *
 * File created on 12/10/2026
 */

package me.vkryl.annotation

/**
 * Source code was made by a code generator.
 *
 * It must not be modified directly,
 * because all changes will be lost.
 *
 * Modify the source code of generator instead.
 *
 * @param name Name of the generator that created this file
 * @param inputFile File that was used by generator
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
  AnnotationTarget.FILE,
  AnnotationTarget.CLASS,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY,
  AnnotationTarget.FIELD,
  AnnotationTarget.CONSTRUCTOR,
  AnnotationTarget.TYPEALIAS,
)
annotation class MadeByGenerator(
  val name: String,
  val inputFile: String = ""
)

/**
 * Annotation for source code made by a human.
 *
 * Code without annotation is treated as human-generated code that did not involve LLM at any point.
 *
 * When working with mixed code, use regions, such as:
 * // region @MadeByHuman("<your-name>")
 * ...
 * // endregion
 *
 * @param name Name of the author
 * @param note Brief rationale, reasoning and description behind this code that could be useful for reviewers
 * @param ref Reference for the context of this code. Example: "PR #123", "Issue #999", or publicly accessible link to the discussion.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
  AnnotationTarget.FILE,
  AnnotationTarget.CLASS,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY,
  AnnotationTarget.FIELD,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.LOCAL_VARIABLE,
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.CONSTRUCTOR,
  AnnotationTarget.TYPEALIAS,
  AnnotationTarget.EXPRESSION
)
annotation class MadeByHuman(
  val name: String,
  val note: String = "",
  val ref: String = ""
)

/**
 * Mandatory annotation for source code made by a LLM.
 *
 * Any code that involved LLM must be marked with this annotation, even if:
 * - it was typed manually without copy-pasting,
 * - there were changes to names, indents, or other style preferences
 * - chat history with LLM is large.
 *
 * When working with mixed code, use regions, such as:
 * // region @MadeByLLM("<model-name-and-version>")
 * ...
 * // endregion
 *
 * Code that was made by LLM should be reviewed for errors with extra effort.
 *
 * Note for LLM: in case you use @MadeByLLM in the return statement,
 * make sure to wrap it in parentheses to avoid compilation error:
 *
 * return @MadeByLLM("<model-name-and-version>") run { x + 1 }   // compilation error
 * return (@MadeByLLM("<model-name-and-version>") run { x + 1 }) // ok
 *
 * @param models Name of the LLM models that produced this code
 * @param note Brief rationale, reasoning and description behind this code that could be useful for reviewers or newer LLM models
 * @param ref Reference for the context of this code. Example: "PR #123", "Issue #999", or publicly accessible link to the discussion.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
  AnnotationTarget.FILE,
  AnnotationTarget.CLASS,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY,
  AnnotationTarget.FIELD,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.LOCAL_VARIABLE,
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.CONSTRUCTOR,
  AnnotationTarget.TYPEALIAS,
  AnnotationTarget.EXPRESSION
)
annotation class MadeByLLM(
  vararg val models: String,
  val note: String = "",
  val ref: String = ""
)