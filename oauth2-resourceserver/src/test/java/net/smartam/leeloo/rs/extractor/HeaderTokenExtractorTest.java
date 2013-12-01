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

package net.smartam.leeloo.rs.extractor;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.junit.Test;

import net.smartam.leeloo.common.OAuth;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class HeaderTokenExtractorTest {

    @Test
    public void testGetAccessToken() throws Exception {

        HttpServletRequest request = createStrictMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("OAuth sometoken");
        replay(request);
        HeaderTokenExtractor hte = new HeaderTokenExtractor();
        Assert.assertEquals("sometoken", hte.getAccessToken(request));
        verify(request);
    }

    @Test
    public void testGetAccessTokenNull() throws Exception {

        HttpServletRequest request = createStrictMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn(null);
        replay(request);
        HeaderTokenExtractor hte = new HeaderTokenExtractor();
        Assert.assertNull(hte.getAccessToken(request));
        verify(request);
    }
}
