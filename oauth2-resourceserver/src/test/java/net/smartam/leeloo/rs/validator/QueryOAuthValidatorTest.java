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

package net.smartam.leeloo.rs.validator;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.junit.Test;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.error.OAuthError;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.utils.OAuthUtils;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class QueryOAuthValidatorTest {


    @Test
    public void testValidateWrongVersion() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn("HMAC-SHA1");
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(new String[] {"access_token"});
        replay(request);
        try {
            QueryOAuthValidator qov = new QueryOAuthValidator();
            qov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Incorrect OAuth version. Found OAuth V1.0.", e.getDescription());
        }
        verify(request);

    }

    @Test
    public void testValidateNoQuery() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(null);
        replay(request);
        try {
            QueryOAuthValidator qov = new QueryOAuthValidator();
            qov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            org.junit.Assert.assertTrue(OAuthUtils.isEmpty(e.getError()));
            Assert.assertEquals("Missing OAuth token.", e.getDescription());
        }
        verify(request);

    }

    @Test
    public void testValidateMultipleTokens() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN))
            .andStubReturn(new String[] {"access_token1", "access_token2"});
        replay(request);
        try {
            QueryOAuthValidator qov = new QueryOAuthValidator();
            qov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Multiple tokens attached.", e.getDescription());
        }
        verify(request);

    }

    @Test
    public void testValidateToken() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(new String[] {"access_token1"});
        replay(request);
        QueryOAuthValidator qov = new QueryOAuthValidator();
        qov.performAllValidations(request);
        verify(request);

    }


}
