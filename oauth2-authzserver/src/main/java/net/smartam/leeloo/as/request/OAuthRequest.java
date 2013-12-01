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

package net.smartam.leeloo.as.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.utils.OAuthUtils;
import net.smartam.leeloo.common.validators.OAuthValidator;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public abstract class OAuthRequest {

    protected HttpServletRequest request;
    protected OAuthValidator validator;
    protected Map<String, Class> validators = new HashMap<String, Class>();

    public OAuthRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        this.request = request;
        validate();
    }

    public OAuthRequest() {
    }

    protected void validate() throws OAuthSystemException, OAuthProblemException {
        try {
            validator = initValidator();
            validator.validateMethod(request);
            validator.validateContentType(request);
            validator.validateRequiredParameters(request);
        } catch (OAuthProblemException e) {
            String redirectUri = request.getParameter(OAuth.OAUTH_REDIRECT_URI);
            if (!OAuthUtils.isEmpty(redirectUri)) {
                e.setRedirectUri(redirectUri);
            }

            throw e;
        }

    }

    protected abstract OAuthValidator initValidator() throws OAuthProblemException, OAuthSystemException;

    public String getParam(String name) {
        return request.getParameter(name);
    }

    public String getRefreshToken() {
        return getParam(OAuth.OAUTH_REFRESH_TOKEN);
    }

    public String getClientId() {
        return getParam(OAuth.OAUTH_CLIENT_ID);
    }

    public String getRedirectURI() {
        return getParam(OAuth.OAUTH_REDIRECT_URI);
    }

    public String getClientSecret() {
        return getParam(OAuth.OAUTH_CLIENT_SECRET);
    }

    public Set<String> getScopes() {
        String scopes = getParam(OAuth.OAUTH_SCOPE);
        return OAuthUtils.decodeScopes(scopes);
    }

}
