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

package org.apache.hcatalog.hcatmix;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.io.IOException;
import java.net.URI;

/**
 * Author: malakar
 */
public class HCatMixHDFSUtils {
    private static Configuration conf;
    private static DistributedFileSystem dfs;
    static {
        conf = new Configuration();
        dfs = new DistributedFileSystem();
        try {
            dfs.initialize(new URI(conf.get("fs.default.name")), conf);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't initialize DFS client");
        }
    }

    public static boolean exists(String path) throws IOException {
        return dfs.exists(new Path(path));
    }

    public static boolean deleteRecursive(String path) throws IOException {
        return dfs.delete(new Path(path), true);
    }
}
