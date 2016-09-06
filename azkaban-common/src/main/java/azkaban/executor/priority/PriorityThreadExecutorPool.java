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

import azkaban.executor.ExecutableNode;

import java.util.Comparator;
import java.util.concurrent.*;

/**
 * This Executor queues up the tasks as per their set priorities.
 */
public class PriorityThreadExecutorPool extends ThreadPoolExecutor implements PriorityExecutorService {

  public PriorityThreadExecutorPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
    this(corePoolSize, maximumPoolSize, keepAliveTime, unit, Executors.defaultThreadFactory(), new AbortPolicy());
  }

  public PriorityThreadExecutorPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                    ThreadFactory threadFactory, RejectedExecutionHandler handler) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
            new PriorityBlockingQueue<Runnable>(corePoolSize,
                    new PriorityFutureTaskComparator()), threadFactory, handler);
  }

  public Future<?> submit(Runnable task, ExecutableNode node) {
    RunnableFuture<Object> pTask = newPriorityTaskFor(task, node);
    execute(pTask);
    return pTask;
  }

  protected RunnableFuture newPriorityTaskFor(Runnable runnable, ExecutableNode node) {
    return new PriorityFutureTask(runnable, node);
  }

  private static class PriorityFutureTaskComparator<T extends PriorityFutureTask> implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t1.compareTo(t2);
    }
  }
}
