/*
 * Copyright (c) 2012. Piraso Alvin R. de Leon. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Piraso licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.piraso.api.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Register a {@link org.piraso.api.entry.Entry} loader
 */
public final class PirasoObjectLoaderRegistry {

    private static final Log LOG = LogFactory.getLog(PirasoObjectLoaderRegistry.class);

    public static final PirasoObjectLoaderRegistry INSTANCE = new PirasoObjectLoaderRegistry();

    static {
        INSTANCE.addEntryLoader(new BasePirasoObjectLoader());
    }

    private List<PirasoObjectLoader> loaders = new LinkedList<PirasoObjectLoader>();

    public void addEntryLoader(PirasoObjectLoader loader) {
        loaders.add(loader);
    }

    public Object loadObject(String className, String content) {
        for(PirasoObjectLoader loader : loaders) {
            try {
                return loader.loadObject(className, content);
            } catch(Exception e) {
                LOG.warn(e.getMessage(), e);
            }
        }

        throw new IllegalStateException(String.format("No loader found to load class %s", className));
    }
}
