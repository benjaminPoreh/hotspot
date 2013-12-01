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
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class BodyOAuthValidatorTest {

    @Test
    public void testValidateInvalidMethod() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn("GET");
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        replay(request);
        try {
            BodyOAuthValidator bov = new BodyOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Incorrect method. POST, PUT, DELETE are supported.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void tesValidateMultipartMessage() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn("multipart/form-data");
        replay(request);
        try {
            BodyOAuthValidator bov = new BodyOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.CodeResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Request is not single part.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void tesValidateInvalidEncoding() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);
        replay(request);
        try {
            BodyOAuthValidator bov = new BodyOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Bad content type.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void tesValidateInvalidOAuthVersion() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn("HMAC-SHA1");
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(new String[] {"access_token"});
        replay(request);
        try {
            BodyOAuthValidator bov = new BodyOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Incorrect OAuth version. Found OAuth V1.0.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void tesValidateTokenMissing() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(null);
        replay(request);
        try {
            BodyOAuthValidator bov = new BodyOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(null, e.getError());
            Assert.assertEquals("Missing OAuth token.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void tesValidateMultipleTokens() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN))
            .andStubReturn(new String[] {"access_token1", "access_token2"});
        replay(request);
        try {
            BodyOAuthValidator bov = new BodyOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Multiple tokens attached.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void tesValidateValidMessage() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(new String[] {"access_token"});
        replay(request);
        BodyOAuthValidator bov = new BodyOAuthValidator();
        bov.performAllValidations(request);
        verify(request);
    }

}
