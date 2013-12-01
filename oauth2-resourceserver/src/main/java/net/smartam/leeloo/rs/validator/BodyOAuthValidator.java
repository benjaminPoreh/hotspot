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

package net.smartam.leeloo.rs.validator;

import javax.servlet.http.HttpServletRequest;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.error.OAuthError;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.utils.OAuthUtils;
import net.smartam.leeloo.common.validators.AbstractValidator;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class BodyOAuthValidator extends AbstractValidator {

    @Override
    public void validateMethod(HttpServletRequest request) throws OAuthProblemException {
        // Check if the method is POST, PUT, or DELETE
        String method = request.getMethod();
        if (!(OAuth.HttpMethod.POST.equals(method) || OAuth.HttpMethod.PUT.equals(method) || OAuth.HttpMethod
            .DELETE.equals(method))) {
            throw OAuthProblemException
                .error(OAuthError.TokenResponse.INVALID_REQUEST)
                .description("Incorrect method. POST, PUT, DELETE are supported.");
        }
    }

    @Override
    public void validateContentType(HttpServletRequest request) throws OAuthProblemException {
        if (OAuthUtils.isMultipart(request)) {
            throw OAuthProblemException.error(OAuthError.CodeResponse.INVALID_REQUEST).
                description("Request is not single part.");
        }
        super.validateContentType(request);
    }


    @Override
    public void validateRequiredParameters(HttpServletRequest request) throws OAuthProblemException {

        if (OAuthUtils.isMultipart(request)) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST).
                description("Request is not single part.");
        }


        String[] tokens = request.getParameterValues(OAuth.OAUTH_TOKEN);
        if (OAuthUtils.hasEmptyValues(tokens)) {
            throw OAuthProblemException.error(null, "Missing OAuth token.");
        }

        if (tokens.length > 1) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST)
                .description("Multiple tokens attached.");
        }

        String oauthVersionDiff = request.getParameter(OAuth.OAUTH_VERSION_DIFFER);
        if (!OAuthUtils.isEmpty(oauthVersionDiff)) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST)
                .description("Incorrect OAuth version. Found OAuth V1.0.");
        }

    }
}
