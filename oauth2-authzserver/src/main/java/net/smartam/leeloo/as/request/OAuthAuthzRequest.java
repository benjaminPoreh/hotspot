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

import net.smartam.leeloo.as.validator.CodeTokenValidator;
import net.smartam.leeloo.as.validator.CodeValidator;
import net.smartam.leeloo.as.validator.TokenValidator;
import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.ResponseType;
import net.smartam.leeloo.common.utils.OAuthUtils;
import net.smartam.leeloo.common.validators.OAuthValidator;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthAuthzRequest extends OAuthRequest {

    public OAuthAuthzRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }

    @Override
    protected OAuthValidator initValidator() throws OAuthProblemException, OAuthSystemException {
        //end user authorization validators
        validators.put(ResponseType.CODE.toString(), CodeValidator.class);
        validators.put(ResponseType.TOKEN.toString(), TokenValidator.class);
        validators.put(ResponseType.CODE_AND_TOKEN.toString(), CodeTokenValidator.class);
        String requestTypeValue = getParam(OAuth.OAUTH_RESPONSE_TYPE);
        if (OAuthUtils.isEmpty(requestTypeValue)) {
            throw OAuthUtils.handleOAuthProblemException("Missing response_type parameter value");
        }
        Class clazz = validators.get(requestTypeValue);
        if (clazz == null) {
            throw OAuthUtils.handleOAuthProblemException("Invalid response_type parameter value");
        }
        return (OAuthValidator)OAuthUtils.instantiateClass(clazz);

    }


}
