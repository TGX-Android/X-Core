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
 * File created on 15/01/2021
 */

package me.vkryl.core.collection;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class LongSet implements Iterable<Long> {
  private final ArrayList<Long> sortedList;

  public LongSet () {
    this.sortedList = new ArrayList<>();
  }

  public LongSet (int initialCapacity) {
    this.sortedList = new ArrayList<>(initialCapacity);
  }

  public void ensureCapacity (int minCapacity) {
    this.sortedList.ensureCapacity(minCapacity);
  }

  public void addAll (long... items) {
    for (long item : items) {
      add(item);
    }
  }

  public void addAll (LongSet items) {
    ensureCapacity(size() + items.size());
    for (long item : items) {
      add(item);
    }
  }

  public boolean add (long item) {
    int position = Collections.binarySearch(sortedList, item);
    if (position >= 0) {
      return false;
    }
    position = (-position) - 1;
    sortedList.add(position, item);
    return true;
  }

  public boolean remove (long item) {
    int position = Collections.binarySearch(sortedList, item);
    if (position < 0) {
      return false;
    }
    sortedList.remove(position);
    return true;
  }

  public boolean replace (long item, long withItem) {
    if (remove(item)) {
      add(withItem);
      return true;
    }
    return false;
  }

  public void clear () {
    sortedList.clear();
  }

  public boolean has (long item) {
    return Collections.binarySearch(sortedList, item) >= 0;
  }

  public int size () {
    return sortedList.size();
  }

  @NonNull
  @Override
  public Iterator<Long> iterator () {
    return sortedList.iterator();
  }

  public long[] toArray () {
    long[] array = new long[sortedList.size()];
    for (int i = 0; i < sortedList.size(); i++) {
      array[i] = sortedList.get(i);
    }
    return array;
  }
}
