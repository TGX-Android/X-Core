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
 * Source code produced by a code generator, such as a Gradle task.
 *
 * Don't modify it directly: all changes will be lost on the next generation.
 * Modify the generator or its input files instead.
 *
 * @param value Name of the generator that produced this code, e.g. Gradle task class name.
 * @param inputs Paths to the input files used by the generator, relative to the project root.
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
  AnnotationTarget.TYPEALIAS
)
annotation class MadeByGenerator(
  val value: String,
  val inputs: Array<String> = []
)

/**
 * Source code written by a human without the assistance of an LLM.
 *
 * Code is attributed to the nearest enclosing annotation. Code without any annotation
 * is treated as human-written code that did not involve an LLM at any point.
 *
 * This annotation is needed only when your code would otherwise be attributed incorrectly:
 * - inside `@MadeByLLM` code, when you change its logic yourself
 *   (style-only changes, such as renames or indents, don't count);
 * - inside `@MadeByHuman` code of another author.
 *
 * If you rewrite a whole `@MadeByLLM` block yourself, and it's not nested
 * inside other annotated code, just remove `@MadeByLLM` instead of adding this annotation.
 *
 * If an LLM assisted in writing your changes, mark them with `@MadeByLLM` instead.
 *
 * To mark only a part of the code, use regions, which can be nested:
 *
 * ```
 * // region @MadeByLLM("<model-name-and-version>")
 * ...
 * // region @MadeByHuman("<your-name>")
 * ...
 * // endregion
 * ...
 * // endregion
 * ```
 *
 * For a single line, a trailing comment can be used instead of a region:
 *
 * ```
 * someFunctionCall(); // @MadeByHuman("<your-name>")
 * ```
 *
 * @param value Name of the author, e.g. GitHub username.
 * @param others Names of additional authors who contributed to this code.
 * @param note Brief rationale, reasoning and description behind this code that could be useful for reviewers.
 * @param ref Reference to the context of this code, e.g. "PR #123", "Issue #999", or a publicly accessible link to the discussion.
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
  val value: String,
  val others: Array<String> = [],
  val note: String = "",
  val ref: String = ""
)

/**
 * Mandatory annotation for source code made with the help of an LLM.
 *
 * Code made with an LLM must be reviewed for errors with extra care.
 *
 * Any code that involved an LLM must be marked with this annotation, even if:
 * - it was typed manually without copy-pasting,
 * - names, indents or other style preferences were changed afterwards,
 * - it went through many iterations in a chat with the LLM,
 * - the LLM only suggested what to fix or how to implement it, and you wrote the code yourself.
 *
 * To mark your own changes inside `@MadeByLLM` code (other than the cases listed above), see `@MadeByHuman`.
 *
 * When working with mixed code (and for statements in Java,
 * where annotations can't be applied to them), use regions:
 *
 * ```
 * // region @MadeByLLM("<model-name-and-version>")
 * ...
 * // endregion
 * ```
 *
 * For a single line, a trailing comment can be used instead of a region:
 *
 * ```
 * someFunctionCall(); // @MadeByLLM("<model-name-and-version>")
 * ```
 *
 * Calls of functions marked with `@MadeByLLM` are not exempt:
 * if the call itself was written by an LLM, it must be marked too.
 *
 * When using `@MadeByLLM` in a return statement, wrap the expression
 * in parentheses, because Kotlin parses `return @Name` as a labeled return:
 *
 * ```
 * return @MadeByLLM("<model-name-and-version>") run { x + 1 }   // compilation error
 * return (@MadeByLLM("<model-name-and-version>") run { x + 1 }) // ok
 * ```
 *
 * @param value Name of the main LLM model that produced this code, e.g. "opus-5.5", "gpt-6-astra", "grok-4.7", "deepseek-v4-pro", "qwen3.8-27b".
 * @param others Names of additional LLM models that produced this code or participated in preliminary reasoning.
 * @param note Brief rationale, reasoning and description behind this code that could be useful for reviewers or newer LLM models.
 * @param ref Reference to the context of this code, e.g. "PR #123", "Issue #999", or a publicly accessible link to the discussion.
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
  val value: String,
  val others: Array<String> = [],
  val note: String = "",
  val ref: String = ""
)