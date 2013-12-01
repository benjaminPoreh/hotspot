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
import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.GitHubTokenResponse;
import net.smartam.leeloo.client.response.OAuthAccessTokenResponse;
import net.smartam.leeloo.client.response.OAuthJSONAccessTokenResponse;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;
import net.smartam.leeloo.exception.ApplicationException;
import net.smartam.leeloo.model.OAuthParams;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
@Controller
@RequestMapping("/get_token")
public class TokenController {

    @RequestMapping
    public ModelAndView authorize(@ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req) throws OAuthSystemException, IOException {

        try {

            Utils.validateTokenParams(oauthParams);

            OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(oauthParams.getTokenEndpoint())
                .setClientId(oauthParams.getClientId())
                .setClientSecret(oauthParams.getClientSecret())
                .setRedirectURI(oauthParams.getRedirectUri())
                .setCode(oauthParams.getAuthzCode())
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .buildQueryMessage();

            OAuthClient client = new OAuthClient(new URLConnectionClient());
            String app = Utils.findCookieValue(req, "app");

            OAuthAccessTokenResponse oauthResponse = null;
            Class<? extends OAuthAccessTokenResponse> cl = OAuthJSONAccessTokenResponse.class;

            if (Utils.FACEBOOK.equals(app)) {
                cl = GitHubTokenResponse.class;
            } else if (Utils.GITHUB.equals(app)) {
                cl = GitHubTokenResponse.class;
            }

            oauthResponse = client.accessToken(request, cl);

            oauthParams.setAccessToken(oauthResponse.getAccessToken());
            oauthParams.setExpiresIn(Utils.isIssued(oauthResponse.getExpiresIn()));
            oauthParams.setRefreshToken(Utils.isIssued(oauthResponse.getRefreshToken()));

            return new ModelAndView("get_resource");

        } catch (ApplicationException e) {
            oauthParams.setErrorMessage(e.getMessage());
            return new ModelAndView("request_token");
        } catch (OAuthProblemException e) {
            StringBuffer sb = new StringBuffer();
            sb.append("</br>");
            sb.append("Error code: ").append(e.getError()).append("</br>");
            sb.append("Error description: ").append(e.getDescription()).append("</br>");
            sb.append("Error uri: ").append(e.getUri()).append("</br>");
            sb.append("State: ").append(e.getState()).append("</br>");
            oauthParams.setErrorMessage(sb.toString());
            return new ModelAndView("get_authz");
        }
    }
}
