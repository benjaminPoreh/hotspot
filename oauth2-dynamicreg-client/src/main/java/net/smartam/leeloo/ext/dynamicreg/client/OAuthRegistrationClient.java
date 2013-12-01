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

package net.smartam.leeloo.ext.dynamicreg.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.smartam.leeloo.client.HttpClient;
import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.ext.dynamicreg.client.response.OAuthClientRegistrationResponse;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthRegistrationClient extends OAuthClient {

    public OAuthRegistrationClient(HttpClient oauthClient) {
        super(oauthClient);
    }

    public OAuthClientRegistrationResponse clientInfo(
        OAuthClientRequest request)
        throws IOException, OAuthSystemException, OAuthProblemException {
        String method = OAuth.HttpMethod.POST;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(OAuth.HeaderType.CONTENT_TYPE, OAuth.ContentType.URL_ENCODED);

        return httpClient.execute(request, headers, method, OAuthClientRegistrationResponse.class);
    }
}
