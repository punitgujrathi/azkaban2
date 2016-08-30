/*
 * Copyright 2012 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package azkaban.executor.priority;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Attaches Priorities with each {@link FutureTask}
 * @param <T>
 */
public class PriorityFutureTask<T> extends FutureTask<T> {
  private int priority;

  public PriorityFutureTask(Callable<T> callable, int priority) {
    super(callable);
    this.priority = priority;
  }

  public PriorityFutureTask(Runnable runnable, T result, int priority) {
    super(runnable, result);
    this.priority = priority;
  }

  public int getPriority() {
    return this.priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  @Override
  public void run() {
    super.run();
  }
}