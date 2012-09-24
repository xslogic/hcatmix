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

import org.apache.commons.lang.StringUtils;
import org.apache.hcatalog.hcatmix.conf.HiveTableSchema;

public class HCatMixUtils {
    /**
     * The returned location would be a directory in case of map reduce mode, otherwise a file in case of
     * local mode
     * @param outputDir
     * @param hiveTableSchema
     * @return
     */
    public static String getDataLocation(final String outputDir, final HiveTableSchema hiveTableSchema) {
        return outputDir + hiveTableSchema.getName();
    }

    /**
     * Get the script file name when PigLoader and HCatStorer is used
     * @param pigScriptDir
     * @param tableName
     * @return
     */
    public static String getHCatStoreScriptName(final String pigScriptDir, final String tableName) {
        return pigScriptDir + tableName + ".load.pig";
    }

    /**
     * Get the script file name when HCatStorer and PigLoader is used
     * @param pigScriptDir
     * @param tableName
     * @return
     */
    public static String getHCatLoadScriptName(final String pigScriptDir, final String tableName) {
        return pigScriptDir + tableName + ".store.pig";
    }

    /**
     * Get the script file name when the default pig PigLoader() and PigStorer() is used
     * @param pigScriptDir
     * @param tableName
     * @return
     */
    public static String getPigLoadStoreScriptName(final String pigScriptDir, final String tableName) {
        return pigScriptDir + tableName + ".load.pig";
    }

    /**
     * Check that the directory name is valid and append a slash to it if required
     * @param outputDir
     * @return
     */
    public static String appendSlashIfRequired(String outputDir) {
        if(StringUtils.isEmpty(outputDir)) {
            throw new IllegalArgumentException("The directory name cannot be null/empty");
        }
        if (!outputDir.endsWith("/")) {
            outputDir += "/";
        }
        return outputDir;
    }
}
