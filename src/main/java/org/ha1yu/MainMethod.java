package org.ha1yu;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class MainMethod {
    private static final Config config = Config.getInstance("config.properties");

    public MainMethod() {
    }

    public static CloseableHttpClient getHttpClient() {
        boolean isProxy = config.getBooleanValue("isProxy");
        SSLContext sslcontext = SSLContextFactory.getSSLContext();
        Registry socketFactoryRegistry = RegistryBuilder.create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(
                        sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
                .build();


//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();

//        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.create().register(HttpHost.DEFAULT_SCHEME_NAME, PlainConnectionSocketFactory.INSTANCE).register("https", new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)).build();

        RequestConfig.Builder builder = RequestConfig.custom().setConnectTimeout(50000).setSocketTimeout(5000).setConnectionRequestTimeout(5000);
        if (isProxy) {
            String proxyIP = config.getStringValue("proxyIP");
            int proxyPort = config.getIntValue("proxyPort");
            HttpHost proxy = new HttpHost(proxyIP, proxyPort, "HTTP");
            builder.setProxy(proxy);
        }

        RequestConfig requestConfig = builder.build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connManager).disableRedirectHandling().build();
        return client;
    }

    public static CloseableHttpResponse doHttpRequest(String url, String method) {
        boolean isAuth = config.getBooleanValue("isAuth");
        boolean isUA = config.getBooleanValue("isUA");

        CloseableHttpResponse response;
        try {
            if (!url.startsWith("http") && !url.startsWith("https")) {
                url = "http://" + url;
            }

            HttpRequestBase requestBase = null;
            switch (method) {
                case "GET":
                    requestBase = new HttpGet(url);
                    break;
                case "HEAD":
                    requestBase = new HttpHead(url);
            }

            String ua;
            if (isAuth) {
                ua = config.getStringValue("username");
                String authPwd = config.getStringValue("password");
                String authStr = ua + ":" + authPwd;
                String authorization = "Basic " + Base64.getEncoder().encodeToString(authStr.getBytes());
                ((HttpRequestBase) requestBase).setHeader("Authorization", authorization);
            }

            if (isUA) {
                ua = config.getStringValue("UA");
                ((HttpRequestBase) requestBase).setHeader("User-Agent", ua);
            }

            response = getHttpClient().execute((HttpUriRequest) requestBase);
        } catch (IOException var10) {
            response = null;
        }

        return response;
    }

    public static String httpRequest(String url, String charset, String method) {
        String result = null;

        try {
            CloseableHttpResponse response = doHttpRequest(url, method);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                switch (statusCode) {
                    case 200:
                    case 500:
                        if (entity != null) {
                            result = EntityUtils.toString(entity, charset);
                        }
                        break;
                    case 301:
                    case 302:
                        Header locationHeader = response.getFirstHeader("Location");
                        if (locationHeader != null) {
                            result = locationHeader.getValue();
                        }
                        break;
                    case 401:
                        result = "401错误，需登录";
                        break;
                    case 404:
                        result = "404错误，地址不存在";
                        break;
                    default:
                        result = "未知错误";
                }

                EntityUtils.consume(entity);
                response.close();
            }
        } catch (IOException var8) {
        }

        return result;
    }

    public static byte[] getPayloadResponse(String targetUrl, String contentType, byte[] payload) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(targetUrl);
        httpPost.setEntity(new ByteArrayEntity(payload));
        byte[] result = null;

        try {
            HttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toByteArray(response.getEntity());
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public static boolean isExists(String path) {
        if (path != null) {
            File file = new File(path);
            return file.exists();
        } else {
            return false;
        }
    }

    public static List<String> readFile(String path) {
        List<String> lt = new ArrayList();
        File file = new File(path);
        Reader reader = null;
        BufferedReader bf = null;
        if (file.exists()) {
            try {
                reader = new InputStreamReader(new FileInputStream(file), "GBK");
                bf = new BufferedReader(reader);
                String line = null;

                while ((line = bf.readLine()) != null) {
                    if (line.trim().length() > 0) {
                        lt.add(line.trim());
                    }
                }

                bf.close();
                reader.close();
            } catch (FileNotFoundException var6) {
                var6.printStackTrace();
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        }

        return lt;
    }
}
