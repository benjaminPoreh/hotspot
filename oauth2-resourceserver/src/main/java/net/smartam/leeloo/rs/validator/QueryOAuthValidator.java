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
public class QueryOAuthValidator extends AbstractValidator {

    @Override
    public void validateContentType(HttpServletRequest request) throws OAuthProblemException {
    }

    @Override
    public void validateMethod(HttpServletRequest request) throws OAuthProblemException {
    }

    @Override
    public void validateRequiredParameters(HttpServletRequest request) throws OAuthProblemException {


        String[] tokens = request.getParameterValues(OAuth.OAUTH_TOKEN);
        if (OAuthUtils.hasEmptyValues(tokens)) {
            throw OAuthProblemException.error("", "Missing OAuth token.");
        }
        if (tokens != null && tokens.length > 1) {
            throw OAuthProblemException
                .error(OAuthError.TokenResponse.INVALID_REQUEST, "Multiple tokens attached.");
        }

        String oauthVersionDiff = request.getParameter(OAuth.OAUTH_VERSION_DIFFER);
        if (!OAuthUtils.isEmpty(oauthVersionDiff)) {
            throw OAuthProblemException
                .error(OAuthError.TokenResponse.INVALID_REQUEST,
                    "Incorrect OAuth version. Found OAuth V1.0.");
        }
    }
}
