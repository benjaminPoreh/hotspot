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

import javax.servlet.http.HttpServletRequest;

import net.smartam.leeloo.as.validator.AssertionValidator;
import net.smartam.leeloo.as.validator.AuthorizationCodeValidator;
import net.smartam.leeloo.as.validator.PasswordValidator;
import net.smartam.leeloo.as.validator.RefreshTokenValidator;
import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;
import net.smartam.leeloo.common.utils.OAuthUtils;
import net.smartam.leeloo.common.validators.OAuthValidator;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthTokenRequest extends OAuthRequest {


    public OAuthTokenRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }

    @Override
    protected OAuthValidator initValidator() throws OAuthProblemException, OAuthSystemException {
        validators.put(GrantType.PASSWORD.toString(), PasswordValidator.class);
        validators.put(GrantType.ASSERTION.toString(), AssertionValidator.class);
        validators.put(GrantType.AUTHORIZATION_CODE.toString(), AuthorizationCodeValidator.class);
        validators.put(GrantType.REFRESH_TOKEN.toString(), RefreshTokenValidator.class);
        String requestTypeValue = getParam(OAuth.OAUTH_GRANT_TYPE);
        if (OAuthUtils.isEmpty(requestTypeValue)) {
            throw OAuthUtils.handleOAuthProblemException("Missing grant_type parameter value");
        }
        Class clazz = validators.get(requestTypeValue);
        if (clazz == null) {
            throw OAuthUtils.handleOAuthProblemException("Invalid grant_type parameter value");
        }
        return (OAuthValidator)OAuthUtils.instantiateClass(clazz);
    }

    public String getPassword() {
        return getParam(OAuth.OAUTH_PASSWORD);
    }

    public String getUsername() {
        return getParam(OAuth.OAUTH_USERNAME);
    }

    public String getAssertion() {
        return getParam(OAuth.OAUTH_ASSERTION);
    }

    public String getAssertionType() {
        return getParam(OAuth.OAUTH_ASSERTION_TYPE);
    }

    public String getCode() {
        return getParam(OAuth.OAUTH_CODE);
    }

    public String getGrantType() {
        return getParam(OAuth.OAUTH_GRANT_TYPE);
    }

}
