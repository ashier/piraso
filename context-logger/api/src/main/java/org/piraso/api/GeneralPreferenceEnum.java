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

package org.piraso.api;

/**
 * General preference enumeration.
 */
public enum GeneralPreferenceEnum implements PreferenceEnum {
    /**
     *  property name for enabling stack trace.
     */
    STACK_TRACE_ENABLED("general.stack.trace.enabled"),

    /**
     * property name for scoped enabled. This means that only monitor request under logging scoped.
     * <p>
     * Example: for sql logging, only when the request requires {@link javax.sql.DataSource} will be monitored.
     * Any other request like, resources, will not result to any logs.
     */
    SCOPE_ENABLED("general.scoped.enabled"),

    /**
     * Determines whether there is no request context
     */
    NO_REQUEST_CONTEXT("general.no.request.context");

    /**
     * the preference property name.
     */
    private final String propertyName;

    /**
     * Construct enum given the enum property name.
     *
     * @param newPropertyName  the property name
     */
    private GeneralPreferenceEnum(final String newPropertyName) {
        this.propertyName = newPropertyName;
    }

    /**
     * Getter method for {@link #propertyName} property.
     *
     * @return the property value
     */
    public String getPropertyName() {
        return propertyName;
    }

    public boolean isLevel() {
        return true;
    }
}
