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

import net.smartam.leeloo.common.OAuth;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class BodyTokenExtractor implements TokenExtractor {

    @Override
    public String getAccessToken(HttpServletRequest request) {
        return request.getParameter(OAuth.OAUTH_TOKEN);
    }

    @Override
    public String getAccessToken(HttpServletRequest request, String tokenName) {
        return request.getParameter(tokenName);
    }
}