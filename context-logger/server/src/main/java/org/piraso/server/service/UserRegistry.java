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

package org.piraso.server.service;

import org.piraso.api.Preferences;
import org.piraso.server.PirasoEntryPoint;
import org.piraso.server.PirasoRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Registry of piraso users.
 */
public interface UserRegistry extends LoggerRegistry {

    boolean isWatched(PirasoEntryPoint request) throws IOException;

    boolean isUserExist(User user);

    User createOrGetUser(PirasoRequest request);

    ResponseLoggerService getLogger(User user);

    Map<User, ResponseLoggerService> getUserLoggerMap();

    /**
     * Associate user with a {@link ResponseLoggerService}.
     *
     * @param user the user
     * @param service the response logger service
     * @throws java.io.IOException on io error
     */
    void associate(User user, ResponseLoggerService service) throws IOException;

    void removeUser(User user) throws IOException;
}
