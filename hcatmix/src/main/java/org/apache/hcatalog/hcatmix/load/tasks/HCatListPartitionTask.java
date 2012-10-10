/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hcatalog.hcatmix.load.tasks;

import org.perf4j.StopWatch;

import java.io.IOException;

/**
* Task to list partitions of an hcatalog table
*/
public class HCatListPartitionTask extends HCatLoadTask {
    HCatListPartitionTask() throws IOException {
        super();
    }

    @Override
    public String getName() {
        return "listPartitions";
    }

    @Override
    public StopWatch doTask() throws Exception {
        StopWatch stopWatch;
        try {
            stopWatch = new StopWatch(getName());
            hiveClient.get().listPartitions(DB_NAME, TABLE_NAME, (short) -1);
            stopWatch.stop();
        } catch (Exception e) {
            LOG.info("Error listing partitions", e);
            numErrors.set(numErrors.get() + 1);
            throw e;
        }
        return stopWatch;
    }
}