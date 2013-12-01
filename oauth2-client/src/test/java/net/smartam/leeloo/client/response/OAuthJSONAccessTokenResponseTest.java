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

package net.smartam.leeloo.client.response;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.smartam.leeloo.client.utils.TestUtils;
import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.error.OAuthError;
import net.smartam.leeloo.common.exception.OAuthProblemException;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthJSONAccessTokenResponseTest extends Assert {

    Logger logger = LoggerFactory.getLogger(OAuthJSONAccessTokenResponse.class);

    @Test
    public void testGetAccessToken() throws Exception {
        logger.info("Running test: testGetAccessToken " + this.getClass().getName());
        OAuthJSONAccessTokenResponse r = null;
        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals(TestUtils.ACCESS_TOKEN, r.getAccessToken());

        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e.getError());
        }
    }

    @Test
    public void testGetExpiresIn() throws Exception {
        OAuthJSONAccessTokenResponse r = null;

        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals(TestUtils.EXPIRES_IN, r.getExpiresIn());

        try {
            new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e.getError());
        }
    }

    @Test
    public void testGetScope() throws Exception {
        OAuthJSONAccessTokenResponse r = null;
        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals(TestUtils.SCOPE, r.getScope());

        try {
            new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e.getError());

        }
    }

    @Test
    public void testGetRefreshToken() throws Exception {
        OAuthJSONAccessTokenResponse r = null;
        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals(TestUtils.REFRESH_TOKEN, r.getRefreshToken());

        try {
            new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e.getError());
        }
    }

    @Test
    public void testSetBody() throws Exception {

        OAuthJSONAccessTokenResponse r = null;
        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        String accessToken = r.getAccessToken();
        String expiresIn = r.getExpiresIn();

        Assert.assertEquals(TestUtils.EXPIRES_IN, expiresIn);
        Assert.assertEquals(TestUtils.ACCESS_TOKEN, accessToken);

        try {
            new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }


    }
}
