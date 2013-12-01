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

package net.smartam.leeloo.ext.dynamicreg.client.request;

import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.ext.dynamicreg.common.OAuthRegistration;


/**
 * OAuth Registration Request
 *
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class OAuthClientRegistrationRequest extends OAuthClientRequest {

    protected OAuthClientRegistrationRequest(String url) {
        super(url);
    }

    public static OAuthRegistrationRequestBuilder location(String url, String type) {
        return new OAuthRegistrationRequestBuilder(url, type);
    }

    public static class OAuthRegistrationRequestBuilder extends OAuthRequestBuilder {

        public OAuthRegistrationRequestBuilder(String url, String type) {
            super(url);
            this.parameters.put(OAuthRegistration.Request.TYPE, type);
        }

        public OAuthRegistrationRequestBuilder setName(String value) {
            this.parameters.put(OAuthRegistration.Request.NAME, value);
            return this;
        }

        public OAuthRegistrationRequestBuilder setUrl(String value) {
            this.parameters.put(OAuthRegistration.Request.URL, value);
            return this;
        }

        public OAuthRegistrationRequestBuilder setDescription(String value) {
            this.parameters.put(OAuthRegistration.Request.DESCRIPTION, value);
            return this;
        }

        public OAuthRegistrationRequestBuilder setIcon(String value) {
            this.parameters.put(OAuthRegistration.Request.ICON, value);
            return this;
        }

        public OAuthRegistrationRequestBuilder setRedirectURI(String uri) {
            this.parameters.put(OAuth.OAUTH_REDIRECT_URI, uri);
            return this;
        }

    }
}
