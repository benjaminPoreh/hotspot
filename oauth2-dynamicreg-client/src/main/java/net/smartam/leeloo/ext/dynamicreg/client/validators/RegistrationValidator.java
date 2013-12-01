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

package net.smartam.leeloo.ext.dynamicreg.client.validators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.smartam.leeloo.client.response.OAuthClientResponse;
import net.smartam.leeloo.client.validator.OAuthClientValidator;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.utils.OAuthUtils;
import net.smartam.leeloo.ext.dynamicreg.common.OAuthRegistration;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class RegistrationValidator extends OAuthClientValidator {

    protected Map<String, String[]> optionalParams = new HashMap<String, String[]>();

    public RegistrationValidator() {
        requiredParams.put(OAuthRegistration.Response.CLIENT_ID, new String[] {});

        optionalParams
            .put(OAuthRegistration.Response.ISSUED_AT, new String[] {OAuthRegistration.Response.EXPIRES_IN});
    }


    private void validateOptionalParams(OAuthClientResponse response) throws OAuthProblemException {
        Set<String> missingParameters = new HashSet<String>();

        for (Map.Entry<String, String[]> requiredParam : optionalParams.entrySet()) {
            String paramName = requiredParam.getKey();
            String val = response.getParam(paramName);
            if (!OAuthUtils.isEmpty(val)) {
                String[] dependentParams = requiredParam.getValue();
                if (!OAuthUtils.hasEmptyValues(dependentParams)) {
                    for (String dependentParam : dependentParams) {
                        val = response.getParam(dependentParam);
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
    public void validateParameters(OAuthClientResponse response) throws OAuthProblemException {
        super.validateParameters(response);
        validateOptionalParams(response);
    }
}
