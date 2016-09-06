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
import azkaban.flow.CommonJobProperties;

import java.util.concurrent.FutureTask;

/**
 * Attaches Priorities with each {@link FutureTask}
 */
public class PriorityFutureTask extends FutureTask implements Comparable<PriorityFutureTask> {
  private ExecutableNode node;

  public PriorityFutureTask(Runnable runnable, ExecutableNode node) {
    super(runnable, null);
    this.node = node;
  }

  @Override
  public void run() {
    super.run();
  }

  @Override
  public int compareTo(PriorityFutureTask that) {
    ExecutableNode firstExecutableNode = this.node;
    ExecutableNode secondExecutableNode = that.node;
    int firstJobPriority = firstExecutableNode.getInputProps().
            getInt(CommonJobProperties.JOB_PRIORITY, 0);
    int secondJobPriority = secondExecutableNode.getInputProps().
            getInt(CommonJobProperties.JOB_PRIORITY, 0);
    if (firstJobPriority == secondJobPriority)  {
      return firstExecutableNode.getNestedId().compareTo(secondExecutableNode.getNestedId());
    }
    return (secondJobPriority - firstJobPriority);
  }
}