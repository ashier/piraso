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

package org.piraso.server;

import org.piraso.api.PreferenceEnum;

/**
 * Preference evaluator
 */
public abstract class PreferenceEvaluator {

    protected ContextPreference context = new PirasoEntryPointContext();

    public ContextPreference getContext() {
        return context;
    }

    public boolean isRegexEnabled(String property) {
        return context != null && context.isRegexEnabled(property);
    }

    public boolean isEnabled(String property) {
        return context != null && context.isEnabled(property);
    }

    public boolean isEnabled(PreferenceEnum pref) {
        return isEnabled(pref.getPropertyName());
    }

    public Integer getIntValue(PreferenceEnum pref) {
        if(context == null) {
            return null;
        }

        return context.getIntValue(pref.getPropertyName());
    }
}
