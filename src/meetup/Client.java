

package meetup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.thoughtworks.xstream.XStream;

import meetup.internal.xstream.XStreamFactory;
import meetup.internal.httpclient.*;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;

/**
 * 
 * 
 *   Meetup.com Java client
 *   
 *   @see http://www.meetup.com/api
 * 
 *   @author Sean C. Sullivan
 *   
 */
public class Client
{
        
        static private final String MEETUP_ENDPOINT_URL = "http://api.meetup.com";
        static private final String OAUTH_REQUEST_TOKEN_URL = "http://www.meetup.com/oauth/request";
        static private final String OAUTH_AUTHORIZE_URL = "http://www.meetup.com/authorize";
        static private final String OAUTH_ACCESS_TOKEN_URL = "http://www.meetup.com/oauth/access";
        
        private HttpClient httpClient;
        private OAuthClient authClient;
        private OAuthServiceProvider authProvider;
        private Token requestToken = new Token();
        private ClientSettings settings;
        private boolean compressionEnabled = false;

        static
        {
                System.getProperties().put("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
                System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "trace");
        }
        
        public Client()
        {
                this(new DefaultHttpClient());
        }
        
        public Client(final HttpClient hClient)
        {
                this.httpClient = hClient;
                
        
                this.setUserAgent("meetup-java-client");
                
                this.setSocketTimeout( (30 * 1000) );
                this.setConnectionTimeout( (5 * 1000) );
                
                
        }

        public void setCompressionEnabled(boolean b)
        {
                compressionEnabled = b;
        }
        

        protected Response sendApiRequestWithOAuth(java.util.Map<String, String>params, String extraPath)
        {
                return sendApiRequest(params, extraPath, true);
        }
        
        protected Response sendApiRequest(String extraPath)
        {
                return sendApiRequest( null, extraPath, false);
        }
        
        
        protected Response sendApiRequest(java.util.Map<String, String> params, String extraPath, boolean useOAuth)
        {
                
                String rsp = sendHttpRequest(MEETUP_ENDPOINT_URL, 
                                                                                        "GET", 
                                                                                        extraPath,
                                                                                        params, 
                                                                                        useOAuth);
                
                Class clazz = null;
                
                if (extraPath.startsWith("events"))
                {
                        clazz = Event.class;
                }
                else if (extraPath.startsWith("groups"))
                {
                        clazz = Group.class;
                }
                else if (extraPath.startsWith("topics"))
                {
                        clazz = Topic.class;
                }
                
                return fromString(rsp, clazz);
                
        }
        
        protected String sendHttpRequest(String baseUrl, 
                                                                        String method, 
                                                                        String extraPath, 
                                                                        java.util.Map<String, String> params, 
                                                                        boolean useOAuth)
        {
                
                if (params == null)
                {
                        params = new HashMap<String, String>();
                }
                
                params.put("key", this.getClientSettings().getMeetupKey());
                
                params.put("format", "xml");
                
                
                String url = baseUrl;
                
                if (extraPath != null)
                {
                        url += "/" + extraPath;
                }
                
                url += buildQueryString(params);
                
                if (useOAuth)
                {
                        try
                        {
                                
                                Token token = this.getClientSettings().getUserSpecificAccessToken();
                                
                                OAuthClient client = getOAuthClient();
                                
                                OAuthAccessor access = this.createOAuthAccessor();
                                
                                access.accessToken = token.getPublicToken();
                                access.tokenSecret = token.getSecret();
                                
                                OAuthMessage responseMsg = client.invoke(access, method, url, params.entrySet());
                                
                                return responseMsg.readBodyAsString();
                                
                        } 
                        catch (Exception e) 
                        {
                                throw new RuntimeException(e);
                        }
                }
                else
                {
                        
                        HttpGet get = new HttpGet(url);
                        
                        System.out.println("url: " + url);
                        
                        HttpResponse response = null;
                        HttpEntity entity = null;
                        
                        try
                        {
                                response = this.getHttpClient().execute(get);
                                int statusCode = response.getStatusLine().getStatusCode();
                                if (statusCode != HttpStatus.SC_OK)
                                {
                                        throw new RuntimeException("unexpected HTTP response, status code = " + statusCode);
                                }
                                else
                                {
                                        entity = response.getEntity();
                                        return EntityUtils.toString(entity);
                                }
                        } 
                        catch (RuntimeException e)
                        {
                                throw e;
                        }
                        catch (Exception e)
                        {
                                throw new RuntimeException(e);
                        } 
                        finally
                        {
                                        try
                                        {
                                                entity.consumeContent();
                                        } 
                                        catch (IOException ignored)
                                        {
                                                // ignore
                                        }
                        }
                        
                }
                
        }

        protected Response fromString(String rsp, Class clazz)
        {
                System.out.println("Server response: " + rsp);
                
                XStream xstream = XStreamFactory.createXStream(clazz);
                
                return (Response) xstream.fromXML(rsp);
                
        }
        

        public Token getRequestToken()
        {
                return this.requestToken;
        }

        protected OAuthClient createOAuthClient()
        {
                
                OAuthClient authClient = new OAuthClient((net.oauth.http.HttpClient) new net.smartam.leeloo.httpclient4.HttpClient4());
                
                return authClient;
        }

        protected OAuthClient getOAuthClient()
        {
                
                if (authClient == null)
                {
                        authClient = createOAuthClient();
                }
                
                return authClient;
        }

        protected OAuthServiceProvider createOAuthServiceProvider()
        {
                
                OAuthServiceProvider p = new OAuthServiceProvider(
                                                                                OAUTH_REQUEST_TOKEN_URL,
                                                                                OAUTH_AUTHORIZE_URL,
                                                                                OAUTH_ACCESS_TOKEN_URL);
                
                return p;
                
        }
        
        public ClientSettings getClientSettings()
        {
                return this.settings; 
        }

        protected OAuthServiceProvider getOAuthServiceProvider()
        {
                if (authProvider == null)
                {
                        authProvider = createOAuthServiceProvider();
                }
                return authProvider;
        }
        
        protected OAuthAccessor createOAuthAccessor()
        {
                
                OAuthConsumer c = createOAuthConsumer();
                
                OAuthAccessor a = new OAuthAccessor(c);
                
                return a;
                
        }

        
        protected OAuthConsumer createOAuthConsumer()
        {
                
                Token consumerToken = this.getClientSettings().getConsumerToken();
                
                String oauthCallBackUrl = this.getClientSettings().getOAuthCallbackUrl();
                
                OAuthConsumer c = new OAuthConsumer(
                                                                        oauthCallBackUrl, 
                                                                        consumerToken.getPublicToken(), 
                                                                        consumerToken.getSecret(), 
                                                                        getOAuthServiceProvider());

                

                if (this.compressionEnabled)
                {
                        c.setProperty(OAuthClient.ACCEPT_ENCODING, "gzip, deflate");
                }
                
                return c;
        }
        
        /**
         * 
         * @return todo
         * 
         */
        public List<Group> getGroups(GroupSearchCriteria criteria)
        {
                
                Map<String, String> params = criteria.getParameterMap();
                
                Response r = sendApiRequest(params, "groups.xml", false);
                
                List<Group> groups = r.getGroups();
                
                return groups;
        }
        
        
        
        protected void checkUserAccessToken() throws UserAuthorizationRequiredException
        {
                
                if ( hasValidUserAccessToken() == false)
                {
                        
                        if ( (this.requestToken != null) && (this.requestToken.isValid()) )
                        {
                                fetchAccessToken();
                        }
                        else
                        {
                                String userAuthorizationUrl = getUserAuthorizationUrl();
                                
                                throw new UserAuthorizationRequiredException(userAuthorizationUrl);
                        }
                }
        }

        public void fetchAccessToken()
        {

                OAuthClient client = getOAuthClient();
                
                OAuthMessage responseMsg = null;
                
                OAuthAccessor access = this.createOAuthAccessor();
                
                
                System.out.println("Request token: " + requestToken);
                System.out.println("Consumer token: " + this.getClientSettings().getConsumerToken());
                
                try
                {
                        
                                //
                                //  request the Access token
                                //
                                
                                access.accessToken = requestToken.getPublicToken();
                                access.requestToken = requestToken.getPublicToken();
                                access.tokenSecret = requestToken.getSecret();
                                
                                responseMsg = client.invoke(access, "GET", OAUTH_ACCESS_TOKEN_URL, null);
                                
                                this.getUserSpecificAccessToken().setPublicToken(responseMsg.getParameter("oauth_token"));
                                this.getUserSpecificAccessToken().setSecret(responseMsg.getParameter("oauth_token_secret"));
                        
                        
                } 
                catch (Exception e) 
                {
                        throw new RuntimeException(e);
                }
        }
        
        public String getUserAuthorizationUrl()
        {
                
                String url = null;

                OAuthClient client = getOAuthClient();
                
                OAuthAccessor access = this.createOAuthAccessor();
                
                try
                {
                        
                        this.requestToken = null;
                        access.requestToken = null;
                        access.accessToken = null;
                        access.tokenSecret = null;

                        //
                        //  send HTTP request to the Request Token URL
                        //
                        
                        
                        client.getRequestToken(access, "GET", null);
                        
                        
                        // store the Request Token values
                        this.requestToken = new Token(access.requestToken, access.tokenSecret.trim());
                        
                        
                        //
                        //
                        // build user authorization URL
                        //
                        //
                        
                        url = OAUTH_AUTHORIZE_URL 
                                                + "?oauth_token=" + requestToken.getPublicToken();
                        
                        if (settings.getOAuthCallbackUrl() != null)
                        {
                                        url = url + "&oauth_callback="
                                                                + settings.getOAuthCallbackUrl();
                        }
                        
                        
                        return url;
                        
                } 
                catch (Exception e) 
                {
                        throw new RuntimeException(e);
                }
                
                
        }

        protected boolean hasValidUserAccessToken()
        {
                return this.getUserSpecificAccessToken().isValid();
        }

        protected Token getUserSpecificAccessToken()
        {
                return this.getClientSettings().getUserSpecificAccessToken(); 
        }

        public void setClientSettings(ClientSettings s)
        {
                this.settings = s;
        }

        public void setRequestToken(Token t)
        {
                this.requestToken = t;
        }
        
        public byte[] get(String url)
        {
                HttpGet get = null;
                HttpResponse response = null;
                byte[] data = null;
                HttpEntity entity = null;
                
                try
                {
                        get = new HttpGet(url);
                        response = this.getHttpClient().execute(get);
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                        {
                                entity = response.getEntity();
                                data = EntityUtils.toByteArray(entity);
                                return data;
                        }
                        else
                        {
                                throw new RuntimeException("HTTP GET failed ("
                                                                + url
                                                                + "), status code = " 
                                                                + response.getStatusLine().getStatusCode());
                        }
                }
                catch (RuntimeException ex)
                {
                        throw ex;
                }
                catch (Exception ex)
                {
                        throw new RuntimeException(ex);
                }
                
                
        }

        public void shutdown()
        {
                try
                {
                        this.httpClient.getConnectionManager().shutdown();
                }
                catch (Exception ignore)
                {
                        // ignored
                }
        }
        


        public void setUserAgent(String ua)
        {
                this.httpClient.getParams().setParameter(AllClientPNames.USER_AGENT, ua);
        }
        
        public void setConnectionTimeout(int milliseconds)
        {
                httpClient.getParams().setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, milliseconds);
        }
        
        public void setSocketTimeout(int milliseconds)
        {
                httpClient.getParams().setIntParameter(AllClientPNames.SO_TIMEOUT, milliseconds);
        }
        

        public List<Event> getEvents(EventSearchCriteria crit)
        {
                
                Response r = sendApiRequest(crit.getParameterMap(), "events.xml", false);
                
                return r.getEvents();
        }
        
        private String buildQueryString(Map<String, String> params)
        {
                StringBuffer query = new StringBuffer();
                
                if (params.size() > 0)
                {
                        query.append("?");
                        
                        for (String key : params.keySet())
                        {
                                query.append(key);
                                query.append("=");
                                query.append(encodeParameter(params.get(key)));
                                query.append("&");
                        }
                        
                        if (query.charAt(query.length() - 1) == '&')
                        {
                                query.deleteCharAt(query.length() - 1);
                        }
                }                       
                
                return query.toString();
        }

        protected String decode(String s)
        {
                try
                {
                        return URLDecoder.decode(s, "UTF-8");
                } 
                catch (UnsupportedEncodingException ex)
                {
                        throw new RuntimeException(ex);
                }
        }
        
        protected String encodeParameter(String s)
        {
                try
                {
                        return URLEncoder.encode(s, "UTF-8");
                }
                catch (Exception ex)
                {
                        throw new RuntimeException(ex);
                }
        }
        
        public String toString()
        {
                if (this.getClientSettings() != null)
                {
                        return this.getClientSettings().toString();
                }
                else
                {
                        return super.toString();
                }
        }

        public List<Topic> getTopics(TopicSearchCriteria criteria)
        {
                
                Map<String, String> params = criteria.getParameterMap();
                
                Response r = sendApiRequest(params, "topics.xml", false);
                
                List<Topic> topics = r.getTopics();
                
                return topics;
        }

        protected HttpClient getHttpClient()
        {
                
                if (this.httpClient instanceof DefaultHttpClient)
                {
                        DefaultHttpClient defaultClient = (DefaultHttpClient) httpClient;
                        
                        defaultClient.removeRequestInterceptorByClass(GzipRequestInterceptor.class);
                        defaultClient.removeResponseInterceptorByClass(GzipResponseInterceptor.class);
                        
                        if (this.isCompressionEnabled())
                        {
                                defaultClient.addRequestInterceptor(GzipRequestInterceptor.getInstance());
                                defaultClient.addResponseInterceptor(GzipResponseInterceptor.getInstance());
                        }
                        
                }

                return this.httpClient;
        }

        public boolean isCompressionEnabled()
        {
                return compressionEnabled;
        }


}
