/*
 * Copyright (c) 2011. Piraso Alvin R. de Leon. All Rights Reserved.
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

package ard.piraso.server;

import ard.piraso.api.GeneralPreferenceEnum;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.*;

/**
 * Test for {@link GeneralPreferenceEvaluator} class.
 */
public class GeneralPreferenceEvaluatorTest {

    private PirasoContext context;

    private GeneralPreferenceEvaluator evaluator;

    @Before
    public void setUp() throws Exception {
        context = mock(PirasoContext.class);
        PirasoContextHolder.setContext(context);

        evaluator = new GeneralPreferenceEvaluator();
    }

    @Test
    public void testIsStackTraceEnabled() throws Exception {
        assertFalse(evaluator.isStackTraceEnabled());

        verify(context, times(1)).isEnabled(GeneralPreferenceEnum.STACK_TRACE_ENABLED.getPropertyName());
    }

    @Test
    public void testIsLoggingScopedEnabled() throws Exception {
        assertFalse(evaluator.isLoggingScopedEnabled());

        verify(context, times(1)).isEnabled(GeneralPreferenceEnum.SCOPE_ENABLED.getPropertyName());
    }

    @Test
    public void testRequestOnScope() throws Exception {
        evaluator.requestOnScope();

        verify(context, times(1)).requestOnScope();
    }
}
