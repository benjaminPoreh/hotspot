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

package net.smartam.leeloo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.GitHubTokenResponse;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;

/**
 * Simple example that shows how to get OAuth 2.0 access token from Facebook
 * using Leeloo OAuth library
 *
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthClientTest {

    public static void main(String[] args) throws OAuthSystemException, IOException {

        try {
            OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("https://graph.facebook.com/oauth/authorize")
                .setClientId("131804060198305")
                .setRedirectURI("http://localhost:8080/")
                .buildQueryMessage();

            //in web application you make redirection to uri:
            System.out.println("Visit: " + request.getLocationUri() + "\nand grant permission");

            System.out.print("Now enter the OAuth code you have received in redirect uri ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String code = br.readLine();

            request = OAuthClientRequest
                .tokenLocation("https://graph.facebook.com/oauth/access_token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId("131804060198305")
                .setClientSecret("3acb294b071c9aec86d60ae3daf32a93")
                .setRedirectURI("http://localhost:8080/")
                .setCode(code)
                .buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            //Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is
            //application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that
            //Own response class is an easy way to deal with oauth providers that introduce modifications to
            //OAuth specification
            GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);

            System.out.println(
                "Access Token: " + oAuthResponse.getAccessToken() + ", Expires in: " + oAuthResponse
                    .getExpiresIn());
        } catch (OAuthProblemException e) {
            System.out.println("OAuth error: " + e.getError());
            System.out.println("OAuth error description: " + e.getDescription());
        }
    }

    @Test
    public void dummyTest() {

    }
}
