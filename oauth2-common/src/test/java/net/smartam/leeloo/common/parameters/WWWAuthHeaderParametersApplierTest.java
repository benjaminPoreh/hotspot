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

package net.smartam.leeloo.common.parameters;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.message.OAuthResponse;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class WWWAuthHeaderParametersApplierTest {

    @Test
    public void testApplyOAuthParameters() throws Exception {

        Map<String, String> params = new HashMap<String, String>();
        params.put("error", "invalid_token");
        params.put("error_uri", "http://www.example.com/error");
        params.put("scope", "s1 s2 s3");
        params.put("empty_param", "");
        params.put("null_param", null);
        params.put("", "some_value");
        params.put(null, "some_value");

        OAuthResponse res = OAuthResponse.status(200).location("").buildQueryMessage();

        OAuthParametersApplier applier = new WWWAuthHeaderParametersApplier();
        res = (OAuthResponse)applier.applyOAuthParameters(res, params);
        Assert.assertNotNull(res);
        String header = res.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE);
        Assert.assertNotNull(header);
        Assert.assertEquals(OAuth.OAUTH_HEADER_NAME
            + " scope=\"s1 s2 s3\",error_uri=\"http://www.example.com/error\",error=\"invalid_token\"",
            header);


    }

}
