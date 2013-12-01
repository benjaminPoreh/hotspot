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

package net.smartam.leeloo.as.validator;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class TokenValidatorTest {
    @Test
    public void testValidateMethod() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);

        replay(request);
        TokenValidator validator = new TokenValidator();
        validator.validateMethod(request);

        verify(request);

        reset(request);

        request = createStrictMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);

        replay(request);
        validator = new TokenValidator();
        validator.validateMethod(request);

        verify(request);

        reset(request);

        request = createStrictMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.DELETE);

        replay(request);
        validator = new TokenValidator();

        try {
            validator.validateMethod(request);
            Assert.fail("Expected validation exception");
        } catch (OAuthProblemException e) {
            //ok, expected
        }

        verify(request);
    }

    @Test
    public void testRequiredParams() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);

        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn("response_type");
        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("client_id");

        replay(request);

        TokenValidator validator = new TokenValidator();
        validator.performAllValidations(request);
        verify(request);
    }
}
