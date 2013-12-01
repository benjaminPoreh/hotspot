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
import net.smartam.leeloo.common.message.OAuthMessage;
import net.smartam.leeloo.common.utils.DummyOAuthMessage;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class QueryParameterApplierTest {

    @Test
    public void testApplyOAuthParameters() throws Exception {

        OAuthParametersApplier app = new QueryParameterApplier();

        Map<String, String> params = new HashMap<String, String>();
        params.put(OAuth.OAUTH_EXPIRES_IN, "3600");
        params.put(OAuth.OAUTH_ACCESS_TOKEN, "token_authz");
        params.put(OAuth.OAUTH_CODE, "code_");
        params.put(OAuth.OAUTH_SCOPE, "read");
        params.put(OAuth.OAUTH_STATE, "state");
        params.put("empty_param", "");
        params.put("null_param", null);

        OAuthMessage message = new DummyOAuthMessage("http://www.example.com/rd", 200);

        app.applyOAuthParameters(message, params);

        String locationURI = message.getLocationUri();
        Assert.assertTrue(locationURI.contains("3600"));
        Assert.assertTrue(locationURI.contains("token_authz"));
        Assert.assertTrue(locationURI.contains("code_"));
        Assert.assertTrue(locationURI.contains("read"));
        Assert.assertTrue(locationURI.contains("state"));

        Assert.assertTrue(!locationURI.contains("empty_param"));
        Assert.assertTrue(!locationURI.contains("null_param"));


    }
}
