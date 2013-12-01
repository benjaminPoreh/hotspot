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

package net.smartam.leeloo.ext.dynamicreg.server.request;

import javax.servlet.http.HttpServletRequest;

import net.smartam.leeloo.as.request.OAuthRequest;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.utils.OAuthUtils;
import net.smartam.leeloo.common.validators.OAuthValidator;
import net.smartam.leeloo.ext.dynamicreg.common.OAuthRegistration;
import net.smartam.leeloo.ext.dynamicreg.server.validator.PullValidator;
import net.smartam.leeloo.ext.dynamicreg.server.validator.PushValidator;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthServerRegistrationRequest extends OAuthRequest {

    private String type;

    private boolean isDiscovered;

    public OAuthServerRegistrationRequest(HttpServletRequest request)
        throws OAuthSystemException, OAuthProblemException {
        this(request, false);
    }

    public OAuthServerRegistrationRequest(HttpServletRequest request, boolean discover)
        throws OAuthSystemException, OAuthProblemException {
        super(request);
        if (discover) {
            discover();
        }
    }

    @Override
    protected OAuthValidator initValidator() throws OAuthProblemException, OAuthSystemException {
        validators.put(OAuthRegistration.Type.PULL, PullValidator.class);
        validators.put(OAuthRegistration.Type.PUSH, PushValidator.class);
        type = getParam(OAuthRegistration.Request.TYPE);
        if (OAuthUtils.isEmpty(type)) {
            throw OAuthUtils.handleOAuthProblemException("Missing type parameter value");
        }
        Class clazz = validators.get(type);
        if (clazz == null) {
            throw OAuthUtils.handleOAuthProblemException("Invalid type parameter value");
        }
        return (OAuthValidator)OAuthUtils.instantiateClass(clazz);
    }

    public void discover() throws OAuthSystemException {
        if (OAuthRegistration.Type.PULL.equals(type)) {
            // discover            
        }
        isDiscovered = true;
    }

    public String getType() {
        return getParam(OAuthRegistration.Request.TYPE);
    }

    public String getName() {
        return getParam(OAuthRegistration.Request.NAME);
    }

    public String getUrl() {
        return getParam(OAuthRegistration.Request.URL);
    }

    public String getDescription() {
        return getParam(OAuthRegistration.Request.DESCRIPTION);
    }

    public String getIcon() {
        return getParam(OAuthRegistration.Request.ICON);
    }

    public String getRedirectURI() {
        return getParam(OAuthRegistration.Request.REDIRECT_URI);
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

}
