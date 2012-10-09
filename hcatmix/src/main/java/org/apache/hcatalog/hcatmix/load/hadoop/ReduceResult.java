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

package org.apache.hcatalog.hcatmix.load.hadoop;

import org.apache.hadoop.io.Writable;
import org.perf4j.GroupedTimingStatistics;
import org.perf4j.TimingStatistics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
* Author: malakar
*/
public class ReduceResult implements Writable {
    private GroupedTimingStatistics statistics;
    private int threadCount;

    public ReduceResult() {
    }

    public ReduceResult(GroupedTimingStatistics statistics, int threadCount) {
        this.statistics = statistics;
        this.threadCount = threadCount;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(threadCount);
        dataOutput.writeInt(statistics.getStatisticsByTag().size());
        for (Map.Entry<String, TimingStatistics> entry : statistics.getStatisticsByTag().entrySet()) {
            dataOutput.writeUTF(entry.getKey());
            TimingStatistics timingStatistics = entry.getValue();
            dataOutput.writeDouble(timingStatistics.getMean());
            dataOutput.writeLong(timingStatistics.getMin());
            dataOutput.writeLong(timingStatistics.getMax());
            dataOutput.writeDouble(timingStatistics.getStandardDeviation());
            dataOutput.writeInt(timingStatistics.getCount());
        }
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        statistics = new GroupedTimingStatistics();
        SortedMap<String, TimingStatistics> statisticsByTag = new TreeMap<String, TimingStatistics>();
        threadCount = dataInput.readInt();
        int size = dataInput.readInt();
        for (int i = 0; i < size; i++) {
            String taskName = dataInput.readUTF();
            double mean = dataInput.readDouble();
            long min = dataInput.readLong();
            long max = dataInput.readLong();
            double stdDev = dataInput.readDouble();
            int count = dataInput.readInt();
            TimingStatistics timingStatistics = new TimingStatistics(mean, stdDev, max, min, count);
            statisticsByTag.put(taskName, timingStatistics);
        }
        statistics.setStatisticsByTag(statisticsByTag);
    }

    public GroupedTimingStatistics getStatistics() {
        return statistics;
    }

    public int getThreadCount() {
        return threadCount;
    }
}