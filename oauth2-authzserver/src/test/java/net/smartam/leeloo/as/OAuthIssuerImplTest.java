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

package net.smartam.leeloo.as;

import junit.framework.Assert;

import org.junit.Test;

import net.smartam.leeloo.as.issuer.MD5Generator;
import net.smartam.leeloo.as.issuer.OAuthIssuer;
import net.smartam.leeloo.as.issuer.OAuthIssuerImpl;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthIssuerImplTest {
    private OAuthIssuer issuer = new OAuthIssuerImpl(new MD5Generator());

    @Test
    public void testAccessToken() throws Exception {
        Assert.assertNotNull(issuer.accessToken());
    }

    @Test
    public void testRefreshToken() throws Exception {
        Assert.assertNotNull(issuer.refreshToken());
    }

    @Test
    public void testAuthorizationCode() throws Exception {
        Assert.assertNotNull(issuer.authorizationCode());
    }
}
