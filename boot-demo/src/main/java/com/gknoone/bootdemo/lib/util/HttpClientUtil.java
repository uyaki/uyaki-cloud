package com.benyuan.bootdemo.lib.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * http请求工具类
 */
public class HttpClientUtil {

    /**
     * get请求
     *
     * @param uri 完整uri
     * @return 请求结果
     */
    public static String httpGet(String uri) {
        try {
            return httpGet(new URIBuilder(uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get请求
     *
     * @param scheme   方案(http/https)
     * @param host     主机
     * @param port     端口
     * @param path     路径
     * @param paramMap 参数
     * @return 请求结果
     */
    public static String httpGet(String scheme, String host, Integer port, String path, Map<String, String> paramMap) {
        return httpGet(getURIBuilder(scheme, host, port, path, paramMap));
    }

    private static String httpGet(URIBuilder uriBuilder) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpClient = httpClientBuilder.build();
        HttpResponse httpResponse;
        try {
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "utf-8").trim();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * post请求
     *
     * @param uri     完整uri
     * @param bodyMap 请求body
     * @return 请求结果
     */
    public static String httpPost(String uri, Map<String, String> bodyMap) {
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            return httpPost(uriBuilder, bodyMap);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * post请求
     *
     * @param scheme   方案(http/https)
     * @param host     主机
     * @param port     端口
     * @param path     路径
     * @param paramMap 参数
     * @param bodyMap  请求body
     * @return 请求结果
     */
    public static String httpPost(String scheme, String host, Integer port, String path, Map<String, String> paramMap, Map<String, String> bodyMap) {
        return httpPost(getURIBuilder(scheme, host, port, path, paramMap), bodyMap);
    }

    private static String httpPost(URIBuilder uriBuilder, Map<String, String> bodyMap) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpClient = httpClientBuilder.build();
        try {
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(bodyMap));
            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "utf-8").trim();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    private static URIBuilder getURIBuilder(String scheme, String host, Integer port, String path, Map<String, String> paramMap) {
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path);
        if (port != null) {
            uriBuilder.setPort(port);
        }
        for (Map.Entry<String, String> param : paramMap.entrySet()) {
            uriBuilder.setParameter(param.getKey(), param.getValue());
        }
        return uriBuilder;
    }
}
