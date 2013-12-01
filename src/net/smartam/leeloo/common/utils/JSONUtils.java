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

package net.smartam.leeloo.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public final class JSONUtils {

    public static String buildJSON(Map<String, String> params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (param.getKey() != null && !"".equals(param.getKey()) && param.getValue() != null && !""
                .equals(param.getValue())) {
                jsonObject.put(param.getKey(), param.getValue());
            }
        }

        return jsonObject.toString();
    }

    public static Map<String, String> parseJSON(String jsonBody) throws JSONException {

        Map<String, String> params = new HashMap<String, String>();
        JSONObject obj = new JSONObject(jsonBody);
        Iterator it = obj.keys();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof String) {
                String key = (String)o;
                params.put(key, obj.getString(key));
            }
        }
        return params;
    }

}
