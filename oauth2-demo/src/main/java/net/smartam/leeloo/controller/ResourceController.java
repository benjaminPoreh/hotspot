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
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.smartam.leeloo.Utils;
import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.utils.OAuthUtils;
import net.smartam.leeloo.model.OAuthParams;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
@Controller
@RequestMapping("/get_resource")
public class ResourceController {

    @RequestMapping
    public ModelAndView authorize(@ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req) {

        try {
            String tokenName = OAuth.OAUTH_TOKEN_DRAFT_0;
            if (Utils.SMART_GALLERY.equals(oauthParams.getApplication())) {
                tokenName = OAuth.OAUTH_TOKEN;
            }
            URL url = new URL(
                oauthParams.getResourceUrl() + "?" + tokenName + "=" + oauthParams.getAccessToken());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if (conn.getResponseCode() == 200) {
                oauthParams.setResource(OAuthUtils.saveStreamAsString(conn.getInputStream()));
            } else {
                oauthParams.setErrorMessage(
                    "Could not access resource: " + conn.getResponseCode() + " " + conn.getResponseMessage());
            }
        } catch (IOException e) {
            oauthParams.setErrorMessage(e.getMessage());
        }

        return new ModelAndView("resource");


    }
}
