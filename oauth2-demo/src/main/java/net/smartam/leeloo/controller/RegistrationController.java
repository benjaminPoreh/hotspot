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

package net.smartam.leeloo.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.smartam.leeloo.Utils;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.exception.ApplicationException;
import net.smartam.leeloo.ext.dynamicreg.client.OAuthRegistrationClient;
import net.smartam.leeloo.ext.dynamicreg.client.request.OAuthClientRegistrationRequest;
import net.smartam.leeloo.ext.dynamicreg.client.response.OAuthClientRegistrationResponse;
import net.smartam.leeloo.ext.dynamicreg.common.OAuthRegistration;
import net.smartam.leeloo.model.OAuthParams;
import net.smartam.leeloo.model.OAuthRegParams;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
@Controller
@RequestMapping("/")
public class RegistrationController {

    @RequestMapping(value = "/register")
    public ModelAndView authorize(@ModelAttribute("oauthRegParams") OAuthRegParams oauthRegParams,
                                  @ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req) throws OAuthSystemException, IOException {


        try {

            Utils.validateRegistrationParams(oauthRegParams);

            OAuthClientRequest request = null;
            if (Utils.REG_TYPE_PULL.equals(oauthRegParams.getRegistrationType())) {
                request = OAuthClientRegistrationRequest
                    .location(oauthRegParams.getRegistrationEndpoint(), OAuthRegistration.Type.PULL)
                    .setUrl(oauthRegParams.getUrl())
                    .buildBodyMessage();
            } else {
                request = OAuthClientRegistrationRequest
                    .location(oauthRegParams.getRegistrationEndpoint(), OAuthRegistration.Type.PUSH)
                    .setName(oauthRegParams.getName())
                    .setUrl(oauthRegParams.getUrl())
                    .setDescription(oauthRegParams.getDescription())
                    .setRedirectURI(oauthRegParams.getRedirectUri())
                    .setIcon(oauthRegParams.getIcon())
                    .buildBodyMessage();
            }


            OAuthRegistrationClient client = new OAuthRegistrationClient(new URLConnectionClient());
            OAuthClientRegistrationResponse response = client.clientInfo(request);

            oauthParams.setClientId(response.getClientId());
            oauthParams.setClientSecret(response.getClientSecret());
            oauthParams.setAuthzEndpoint(oauthRegParams.getAuthzEndpoint());
            oauthParams.setTokenEndpoint(oauthRegParams.getTokenEndpoint());
            oauthParams.setApplication(oauthRegParams.getApplication());
            oauthParams.setRedirectUri(oauthRegParams.getRedirectUri());

            return new ModelAndView("get_authz");


        } catch (ApplicationException e) {
            oauthRegParams.setErrorMessage(e.getMessage());
            return new ModelAndView("register");
        } catch (OAuthProblemException e) {
            oauthRegParams.setErrorMessage(e.getMessage());
            return new ModelAndView("register");
        }
    }

}
