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

import java.util.HashMap;
import java.util.Map;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.message.OAuthMessage;
import net.smartam.leeloo.common.utils.OAuthUtils;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class QueryParameterApplier implements OAuthParametersApplier {

    public OAuthMessage applyOAuthParameters(OAuthMessage message, Map<String, String> params) {

        String messageUrl = message.getLocationUri();
        if (messageUrl != null) {
            boolean containsQuestionMark = messageUrl.contains("?");
            StringBuffer url = new StringBuffer(messageUrl);

            //apply uri fragment component if exist access_toke param
            Map<String, String> fragmentParams = new HashMap<String, String>();
            if (params.containsKey(OAuth.OAUTH_ACCESS_TOKEN)) {
                fragmentParams.put(OAuth.OAUTH_ACCESS_TOKEN, params.remove(OAuth.OAUTH_ACCESS_TOKEN));

                if (params.containsKey(OAuth.OAUTH_EXPIRES_IN)) {
                    fragmentParams.put(OAuth.OAUTH_EXPIRES_IN, params.remove(OAuth.OAUTH_EXPIRES_IN));
                }
            }

            StringBuffer query = new StringBuffer(OAuthUtils.format(params.entrySet(), "UTF-8"));
            String fragmentQuery = "";
            if (fragmentParams.containsKey(OAuth.OAUTH_ACCESS_TOKEN)) {
                fragmentQuery = OAuthUtils.format(fragmentParams.entrySet(), "UTF-8");
            }

            if (!OAuthUtils.isEmpty(query.toString())) {
                if (containsQuestionMark) {
                    url.append("&").append(query);
                } else {
                    url.append("?").append(query);
                }
            }

            if (!OAuthUtils.isEmpty(fragmentQuery)) {
                url.append("#").append(fragmentQuery);
            }

            message.setLocationUri(url.toString());
        }
        return message;
    }
}
