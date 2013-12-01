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
public class HeaderOAuthValidatorTest {


    @Test
    public void testValidateNoHeader() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn(null);
        replay(request);
        try {
            HeaderOAuthValidator bov = new HeaderOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            org.junit.Assert.assertTrue(OAuthUtils.isEmpty(e.getError()));
            Assert.assertEquals("Missing authorization header.", e.getDescription());
        }
        verify(request);

    }

    @Test
    public void testValidateInvalidHeader() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Basic arawersadf");
        replay(request);
        try {
            HeaderOAuthValidator bov = new HeaderOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            org.junit.Assert.assertTrue(OAuthUtils.isEmpty(e.getError()));
            Assert.assertEquals("Incorrect authorization method.", e.getDescription());
        }
        verify(request);

    }


    @Test
    public void testValidateValidHeaderMissingField() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("OAuth  ");
        replay(request);
        try {
            HeaderOAuthValidator bov = new HeaderOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Missing required parameter.", e.getDescription());
        }
        verify(request);

    }


    @Test
    public void testValidateValidHeaderWrongVersion() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION))
            .andStubReturn("OAuth sdfsadfsadf,oauth_signature_method=\"HMAC-SHA1\"");
        replay(request);
        try {
            HeaderOAuthValidator bov = new HeaderOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Incorrect OAuth version. Found OAuth V1.0.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testValidateValidHeader() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("OAuth sdfsadfsadf");
        replay(request);
        HeaderOAuthValidator bov = new HeaderOAuthValidator();
        bov.performAllValidations(request);

        verify(request);
    }

}
