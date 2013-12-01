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

package net.smartam.leeloo.as.response;

import org.junit.Assert;
import org.junit.Test;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.error.OAuthError;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.message.OAuthResponse;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthASResponseTest {

    @Test
    public void testAuthzResponse() throws Exception {
        OAuthResponse oAuthResponse = OAuthASResponse.authorizationResponse(200)
            .location("http://www.example.com")
            .setCode("code")
            .setAccessToken("access_111")
            .setExpiresIn("400")
            .setState("ok")
            .setParam("testValue", "value2")
            .buildQueryMessage();

        String url = oAuthResponse.getLocationUri();

        Assert.assertEquals("http://www.example.com?testValue=value2&state=ok&code=code"
            + "#expires_in=400&access_token=access_111", url);
        Assert.assertEquals(200, oAuthResponse.getResponseStatus());

    }

    @Test
    public void testTokenResponse() throws Exception {

        OAuthResponse oAuthResponse = OAuthASResponse.tokenResponse(200).setAccessToken("access_token")
            .setExpiresIn("200").setRefreshToken("refresh_token2")
            .buildBodyMessage();

        String body = oAuthResponse.getBody();
        Assert.assertEquals(
            "expires_in=200&refresh_token=refresh_token2&access_token=access_token",
            body);

    }

    @Test
    public void testTokenResponseAdditionalParam() throws Exception {

        OAuthResponse oAuthResponse = OAuthASResponse.tokenResponse(200).setAccessToken("access_token")
            .setExpiresIn("200").setRefreshToken("refresh_token2").setParam("some_param", "new_param")
            .buildBodyMessage();

        String body = oAuthResponse.getBody();
        Assert.assertEquals(
            "some_param=new_param&expires_in=200&refresh_token=refresh_token2&access_token=access_token",
            body);

    }

    @Test
    public void testErrorResponse() throws Exception {

        OAuthProblemException ex = OAuthProblemException
            .error(OAuthError.CodeResponse.ACCESS_DENIED, "Access denied")
            .setParameter("testparameter", "testparameter_value")
            .scope("album")
            .uri("http://www.example.com/error");

        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(400).error(ex).buildJSONMessage();

        Assert.assertEquals(
            "{\"error_uri\":\"http:\\/\\/www.example.com\\/error\",\"error\":\"access_denied\",\""
                + "error_description\":\"Access denied\"}",
            oAuthResponse.getBody());


        oAuthResponse = OAuthResponse.errorResponse(500)
            .location("http://www.example.com/redirect?param2=true").error(ex).buildQueryMessage();
        Assert.assertEquals(
            "http://www.example.com/redirect?param2=true&error_uri=http%3A%2F%2Fwww.example.com%2Ferror"
                + "&error=access_denied&error_description=Access+denied",
            oAuthResponse.getLocationUri());
    }

    @Test
    public void testErrorResponse2() throws Exception {
        OAuthProblemException ex = OAuthProblemException
            .error(OAuthError.CodeResponse.ACCESS_DENIED, "Access denied")
            .setParameter("testparameter", "testparameter_value")
            .scope("album")
            .uri("http://www.example.com/error");

        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(500)
            .location("http://www.example.com/redirect?param2=true").error(ex).buildQueryMessage();
        Assert.assertEquals(
            "http://www.example.com/redirect?param2=true&error_uri=http%3A%2F%2Fwww.example.com%2Ferror"
                + "&error=access_denied&error_description=Access+denied",
            oAuthResponse.getLocationUri());
    }

    @Test
    public void testHeaderResponse() throws Exception {
        OAuthResponse oAuthResponse = OAuthASResponse.authorizationResponse(400).setCode("oauth_code")
            .setState("state_ok")
            .buildHeaderMessage();

        String header = oAuthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE);
        Assert.assertEquals("OAuth state=\"state_ok\",code=\"oauth_code\"", header);

        header = oAuthResponse.getHeaders().get(OAuth.HeaderType.WWW_AUTHENTICATE);
        Assert.assertEquals("OAuth state=\"state_ok\",code=\"oauth_code\"", header);
    }

}
