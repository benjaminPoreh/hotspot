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

package net.smartam.leeloo.ext.dynamicreg.common;

/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public final class OAuthRegistration {
    private OAuthRegistration() {
    }

    public static final class Type {
        private Type() {
        }

        public static final String PUSH = "push";
        public static final String PULL = "pull";
    }

    public static final class Request {
        private Request() {
        }

        public static final String TYPE = "type";
        public static final String NAME = "name";
        public static final String URL = "url";
        public static final String DESCRIPTION = "description";
        public static final String ICON = "icon";
        public static final String REDIRECT_URI = "redirect_uri";
    }

    public static final class Response {
        private Response() {
        }

        public static final String CLIENT_ID = "client_id";
        public static final String CLIENT_SECRET = "client_secret";
        public static final String ISSUED_AT = "issued_at";
        public static final String EXPIRES_IN = "expires_in";
    }

}
