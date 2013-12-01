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

package net.smartam.leeloo.ext.dynamicreg.client.response;

import org.codehaus.jettison.json.JSONException;

import net.smartam.leeloo.client.response.OAuthClientResponse;
import net.smartam.leeloo.common.error.OAuthError;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.utils.JSONUtils;
import net.smartam.leeloo.ext.dynamicreg.client.validators.RegistrationValidator;
import net.smartam.leeloo.ext.dynamicreg.common.OAuthRegistration;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthClientRegistrationResponse extends OAuthClientResponse {

    public OAuthClientRegistrationResponse() {
    }

    @Override
    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        validator = new RegistrationValidator();
        super.init(body, contentType, responseCode);
    }

    @Override
    public String getParam(String param) {
        return parameters.get(param);
    }

    protected void setBody(String body) throws OAuthProblemException {
        try {
            this.body = body;
            parameters = JSONUtils.parseJSON(body);
        } catch (JSONException e) {
            throw OAuthProblemException.error(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE,
                "Invalid response! Response body is not application/json encoded");
        }
    }

    protected void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getClientId() {
        return parameters.get(OAuthRegistration.Response.CLIENT_ID);
    }

    public String getClientSecret() {
        return parameters.get(OAuthRegistration.Response.CLIENT_SECRET);
    }

    public String getIssuedAt() {
        return parameters.get(OAuthRegistration.Response.ISSUED_AT);
    }

    public String getExpiresIn() {
        return parameters.get(OAuthRegistration.Response.EXPIRES_IN);
    }

}
