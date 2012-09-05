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

package ard.piraso.api.log4j;

import ard.piraso.api.Preferences;
import org.apache.log4j.Level;

/**
 * Preference wrapper for log4j
 */
public class Log4jPreferenceWrapper {

    private Preferences pref;

    public Log4jPreferenceWrapper(Preferences pref) {
        this.pref = pref;
    }

    public void info(String logger) {
        add(logger, Level.INFO);
    }

    public void debug(String logger) {
        add(logger, Level.DEBUG);
    }

    public void warn(String logger) {
        add(logger, Level.WARN);
    }

    public void error(String logger) {
        add(logger, Level.ERROR);
    }

    public void fatal(String logger) {
        add(logger, Level.FATAL);
    }

    public void add(String logger) {
        add(logger, Level.ALL);
    }

    public void add(String logger, Level level) {
        pref.addProperty("log4j." + logger + "." + level.toString(), true);
    }
}
