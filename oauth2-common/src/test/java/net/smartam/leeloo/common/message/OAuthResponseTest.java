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

package net.smartam.leeloo.common.message;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthResponseTest {


    @Test
    public void testErrorResponse() throws Exception {
        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(400)
            .setError("error")
            .setRealm("album")
            .setState("ok")
            .setErrorDescription("error_description")
            .setErrorUri("http://example-uri")
            .setParam("param", "value")
            .buildJSONMessage();

        String body = oAuthResponse.getBody();
        Assert.assertEquals(
            "{\"error_uri\":\"http:\\/\\/example-uri\",\"error\":\"error\",\"param\":\"value\","
                + "\"realm\":\"album\",\"state\":\"ok\",\"error_description\":\"error_description\"}",
            body);
    }


}
