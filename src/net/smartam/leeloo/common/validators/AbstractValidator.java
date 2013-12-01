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

package net.smartam.leeloo.common.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.utils.OAuthUtils;
import static net.smartam.leeloo.common.utils.OAuthUtils.handleNotAllowedParametersOAuthException;
import static net.smartam.leeloo.common.utils.OAuthUtils.handleOAuthProblemException;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
//todo add client secret in header, sect 2.1
public abstract class AbstractValidator implements OAuthValidator {

    protected List<String> requiredParams = new ArrayList<String>();
    protected Map<String, String[]> optionalParams = new HashMap<String, String[]>();
    protected List<String> notAllowedParams = new ArrayList<String>();


    @Override
    public void validateMethod(HttpServletRequest request) throws OAuthProblemException {
        if (!request.getMethod().equals(OAuth.HttpMethod.POST)) {
            throw handleOAuthProblemException("Method not set to POST.");
        }
    }

    @Override
    public void validateContentType(HttpServletRequest request) throws OAuthProblemException {
        String contentType = request.getContentType();
        if (!OAuthUtils.hasContentType(contentType, OAuth.ContentType.URL_ENCODED)) {
            throw handleOAuthProblemException("Bad content type.");
        }
    }

    @Override
    public void validateRequiredParameters(HttpServletRequest request) throws OAuthProblemException {
        Set<String> missingParameters = new HashSet<String>();
        for (String requiredParam : requiredParams) {
            String val = request.getParameter(requiredParam);
            if (OAuthUtils.isEmpty(val)) {
                missingParameters.add(requiredParam);
            }
        }
        if (!missingParameters.isEmpty()) {
            throw OAuthUtils.handleMissingParameters(missingParameters);
        }
    }

    @Override
    public void validateOptionalParameters(HttpServletRequest request) throws OAuthProblemException {

        Set<String> missingParameters = new HashSet<String>();

        for (Map.Entry<String, String[]> requiredParam : optionalParams.entrySet()) {
            String paramName = requiredParam.getKey();
            String val = request.getParameter(paramName);
            if (!OAuthUtils.isEmpty(val)) {
                String[] dependentParams = requiredParam.getValue();
                if (!OAuthUtils.hasEmptyValues(dependentParams)) {
                    for (String dependentParam : dependentParams) {
                        val = request.getParameter(dependentParam);
                        if (OAuthUtils.isEmpty(val)) {
                            missingParameters.add(dependentParam);
                        }
                    }
                }
            }
        }

        if (!missingParameters.isEmpty()) {
            throw OAuthUtils.handleMissingParameters(missingParameters);
        }
    }

    @Override
    public void validateNotAllowedParameters(HttpServletRequest request) throws OAuthProblemException {
        List<String> notAllowedParameters = new ArrayList<String>();
        for (String requiredParam : notAllowedParams) {
            String val = request.getParameter(requiredParam);
            if (!OAuthUtils.isEmpty(val)) {
                notAllowedParameters.add(requiredParam);
            }
        }
        if (!notAllowedParameters.isEmpty()) {
            throw handleNotAllowedParametersOAuthException(notAllowedParameters);
        }
    }

    @Override
    public void performAllValidations(HttpServletRequest request) throws OAuthProblemException {
        this.validateContentType(request);
        this.validateMethod(request);
        this.validateRequiredParameters(request);
        this.validateOptionalParameters(request);
        this.validateNotAllowedParameters(request);
    }
}
