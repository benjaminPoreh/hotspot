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

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.OAuthMessage;
import net.smartam.leeloo.common.utils.OAuthUtils;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class WWWAuthHeaderParametersApplier implements OAuthParametersApplier {

    public OAuthMessage applyOAuthParameters(OAuthMessage message, Map<String, String> params)
        throws OAuthSystemException {
        String header = OAuthUtils.encodeOAuthHeader(params);
        message.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE, header);
        return message;
    }
}
