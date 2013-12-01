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

package net.smartam.leeloo.httpclient4;

import java.net.URI;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.smartam.leeloo.client.HttpClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.OAuthClientResponse;
import net.smartam.leeloo.client.response.OAuthClientResponseFactory;
import net.smartam.leeloo.common.OAuth;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.utils.OAuthUtils;


/**
 * Exemplar HttpClient4
 *
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class HttpClient4 implements HttpClient {

    private org.apache.http.client.HttpClient client = new DefaultHttpClient();

    public HttpClient4() {
    }

    public HttpClient4(org.apache.http.client.HttpClient client) {
        this.client = client;
    }

    public <T extends OAuthClientResponse> T execute(OAuthClientRequest request,
                                                     Map<String, String> headers,
                                                     String requestMethod,
                                                     Class<T> responseClass)
        throws OAuthSystemException, OAuthProblemException {

        try {
            URI location = new URI(request.getLocationUri());
            HttpRequestBase req = null;
            String responseBody = "";

            if (!OAuthUtils.isEmpty(requestMethod) && OAuth.HttpMethod.POST.equals(requestMethod)) {
                req = new HttpPost(location);
                HttpEntity entity = new StringEntity(request.getBody());
                ((HttpPost)req).setEntity(entity);
            } else {
                req = new HttpGet(location);
            }
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    req.setHeader(header.getKey(), header.getValue());
                }
            }
            HttpResponse response = client.execute(req);
            Header contentTypeHeader = null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
                contentTypeHeader = entity.getContentType();
            }
            String contentType = null;
            if (contentTypeHeader != null) {
                contentType = contentTypeHeader.toString();
            }

            return OAuthClientResponseFactory
                .createCustomResponse(responseBody, contentType, response.getStatusLine().getStatusCode(),
                    responseClass);
        } catch (Exception e) {
            throw new OAuthSystemException(e);
        }

    }
}
