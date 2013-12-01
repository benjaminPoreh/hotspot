/**
 *       Copyright 2010 Newcastle University
 *           Maciej Machulak, Lukasz Moren
 *
 *          http://research.ncl.ac.uk/smart/
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.smartam.leeloo.common.parameters;

import java.util.Map;

import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.OAuthMessage;

/**
 * Applies given parameters to the OAuth message.
 * Provided implementations include OAuth parameters in one of those:
 * <ul>
 * <li>HTTP request URI Query</li>
 * <li>HTTP request entity-body with application/x-www-form-urlencoded encoding</li>
 * <li>HTTP request entity-body with application/json encoding</li>
 * <li>HTTP request Authorization/WWW-Authenticate header</li>
 * </ul>
 * <p/>
 * Additional implementations can be provided.
 *
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public interface OAuthParametersApplier {

    OAuthMessage applyOAuthParameters(OAuthMessage message, Map<String, String> params) throws
        OAuthSystemException;
}
