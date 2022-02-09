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

package me.vkryl.core.util;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.vkryl.core.lambda.FutureBool;

public final class JobList {
  private final FutureBool canExecute;
  private final List<Runnable> list;

  private volatile boolean value;

  public JobList (FutureBool state) {
    this.canExecute = state;
    this.list = new ArrayList<>();
    synchronized (list) {
      this.value = state.get();
    }
  }

  @Nullable
  private Runnable onAdd, onRemove;

  public JobList onAddRemove (Runnable onAdd, Runnable onRemove) {
    this.onAdd = onAdd;
    this.onRemove = onRemove;
    return this;
  }

  private boolean value () {
    boolean newValue = canExecute.get();
    if (this.value != newValue) {
      this.value = newValue;
      return newValue;
    }
    if (newValue && !list.isEmpty())
      throw new IllegalStateException();
    return newValue;
  }

  public void add (Runnable runnable) {
    int removedCount = 0;
    boolean added = false;
    synchronized (list) {
      if (value()) {
        runnable.run();
        removedCount = execute();
      } else {
        list.add(runnable);
        added = true;
      }
    }
    if (added) {
      if (onAdd != null)
        onAdd.run();
    } else if (onRemove != null) {
      for (int i = 0; i < removedCount; i++)
        onRemove.run();
    }
  }

  public void trigger () {
    trigger(false);
  }

  public void trigger (boolean force) {
    int removedCount;
    synchronized (list) {
      removedCount = value() || force ? execute() : 0;
    }
    if (onRemove != null) {
      for (int i = 0; i < removedCount; i++)
        onRemove.run();
    }
  }

  public boolean isEmpty () {
    synchronized (list) {
      return list.isEmpty();
    }
  }

  private int execute () {
    final int count = list.size();
    if (count == 0)
      return 0;
    List<Runnable> pending = new ArrayList<>(list);
    list.clear();
    for (int i = count - 1; i >= 0; i--) {
      pending.get(i).run();
    }
    return count;
  }
}
