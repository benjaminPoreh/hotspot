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

package net.smartam.leeloo.rs.request;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.error.OAuthError;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.ParameterStyle;
import net.smartam.leeloo.common.utils.OAuthUtils;
import net.smartam.leeloo.common.validators.OAuthValidator;
import net.smartam.leeloo.rs.extractor.BodyTokenExtractor;
import net.smartam.leeloo.rs.extractor.HeaderTokenExtractor;
import net.smartam.leeloo.rs.extractor.QueryTokenExtractor;
import net.smartam.leeloo.rs.extractor.TokenExtractor;
import net.smartam.leeloo.rs.validator.BodyOAuthValidator;
import net.smartam.leeloo.rs.validator.HeaderOAuthValidator;
import net.smartam.leeloo.rs.validator.QueryOAuthValidator;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthAccessResourceRequest {

    private HttpServletRequest request;
    private ParameterStyle[] parameterStyles = new ParameterStyle[] {OAuth.DEFAULT_PARAMETER_STYLE};
    private ParameterStyle usedParameterStyle;

    private Map<ParameterStyle, Class> extractors = new HashMap<ParameterStyle, Class>();
    private Map<ParameterStyle, Class> validators = new HashMap<ParameterStyle, Class>();

    private TokenExtractor extractor;

    {
        extractors.put(ParameterStyle.HEADER, HeaderTokenExtractor.class);
        extractors.put(ParameterStyle.BODY, BodyTokenExtractor.class);
        extractors.put(ParameterStyle.QUERY, QueryTokenExtractor.class);

        validators.put(ParameterStyle.HEADER, HeaderOAuthValidator.class);
        validators.put(ParameterStyle.BODY, BodyOAuthValidator.class);
        validators.put(ParameterStyle.QUERY, QueryOAuthValidator.class);
    }

    public OAuthAccessResourceRequest(HttpServletRequest request)
        throws OAuthSystemException, OAuthProblemException {
        this(request, OAuth.DEFAULT_PARAMETER_STYLE);
    }

    public OAuthAccessResourceRequest(HttpServletRequest request, ParameterStyle... parameterStyles)
        throws OAuthSystemException, OAuthProblemException {
        this.request = request;
        this.parameterStyles = parameterStyles;
        this.validate();
    }

    public String getAccessToken() throws OAuthSystemException {
        return extractor.getAccessToken(request);
    }

    private void validate() throws OAuthSystemException, OAuthProblemException {

        int foundValidStyles = 0;
        boolean lackAuthInfo = false;
        OAuthProblemException ex = null;
        for (ParameterStyle style : parameterStyles) {
            try {

                OAuthValidator validator = instantiateValidator(style);
                validator.validateContentType(request);
                validator.validateMethod(request);
                validator.validateRequiredParameters(request);

                usedParameterStyle = style;
                foundValidStyles++;
            } catch (OAuthProblemException e) {
                //request lacks any authentication information?
                if (OAuthUtils.isEmpty(e.getError())) {
                    lackAuthInfo = true;
                } else {
                    ex = OAuthProblemException.error(e.getError(), e.getDescription());
                }
            }
        }

        if (foundValidStyles > 1) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST,
                "Found more than one mechanism for authenticating client");
        }

        if (ex != null) {
            throw ex;
        }

        if (foundValidStyles == 0 && lackAuthInfo) {
            throw OAuthProblemException.error(null, "OAuth parameters were not found");
        }

        if (foundValidStyles == 0) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST,
                "OAuth parameters were not found");
        }

        instantiateExtractor(usedParameterStyle);
    }

    private OAuthValidator instantiateValidator(ParameterStyle ps) throws OAuthSystemException {
        Class clazz = validators.get(ps);
        if (clazz == null) {
            throw new OAuthSystemException("Cannot instantiate a message validator.");
        }
        return (OAuthValidator)OAuthUtils.instantiateClass(clazz);
    }

    private void instantiateExtractor(ParameterStyle ps) throws OAuthSystemException {
        Class clazz = extractors.get(ps);
        if (clazz == null) {
            throw new OAuthSystemException("Cannot instantiate a token extractor.");
        }
        extractor = (TokenExtractor)OAuthUtils.instantiateClass(clazz);
    }
}
