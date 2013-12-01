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

package net.smartam.leeloo.common;

import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamReader;

import org.codehaus.jettison.AbstractXMLStreamReader;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.junit.Assert;
import org.junit.Test;

import net.smartam.leeloo.common.error.OAuthError;
import net.smartam.leeloo.common.utils.JSONUtils;
import net.smartam.leeloo.common.utils.OAuthUtils;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthUtilsTest extends Assert {

    @Test
    public void testBuildJSON() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put(OAuthError.OAUTH_ERROR, OAuthError.TokenResponse.INVALID_REQUEST);

        String json = JSONUtils.buildJSON(params);

        JSONObject obj = new JSONObject(json);

        AbstractXMLStreamReader reader = new MappedXMLStreamReader(obj);

        assertEquals(XMLStreamReader.START_ELEMENT, reader.next());
        assertEquals(OAuthError.OAUTH_ERROR, reader.getName().getLocalPart());

        assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, reader.getText());
        assertEquals(XMLStreamReader.CHARACTERS, reader.next());
        assertEquals(XMLStreamReader.END_ELEMENT, reader.next());
        assertEquals(XMLStreamReader.END_DOCUMENT, reader.next());

    }

    @Test
    public void testEncodeOAuthHeader() throws Exception {

        Map<String, String> entries = new HashMap<String, String>();
        entries.put("realm", "Some Example Realm");
        entries.put("error", "invalid_token");

        String header = OAuthUtils.encodeOAuthHeader(entries);
        assertEquals("OAuth error=\"invalid_token\",realm=\"Some Example Realm\"", header);

    }
}
