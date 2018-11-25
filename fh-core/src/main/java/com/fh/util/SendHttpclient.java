package com.fh.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class SendHttpclient {
    //delete动作
    public static String sendDelete(String url,Integer id,Map<String,String> headers,boolean isValidate){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        url += "/"+id;
        HttpDelete httpDelete = new HttpDelete(url);
        buildHeaders(headers, httpDelete,isValidate);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = client.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != response){
                try {
                    response.close();
                    response = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != httpDelete){
                httpDelete.releaseConnection();
            }
            if(null != client){
                try {
                    client.close();
                    client = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  result;
    }

    //put动作
    public static String sendPut(String url,Map<String,String> params,Map<String,String> headers,boolean isValidate){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPut httpPut = new HttpPut(url);
        Gson gson = new Gson();
        String paramJson = gson.toJson(params);
        StringEntity stringEntity = new StringEntity(paramJson, "utf-8");
        stringEntity.setContentType("application/json");
        httpPut.setEntity(stringEntity);
        buildHeaders(headers, httpPut,isValidate);
        CloseableHttpResponse response=null;
        String result = "";
        try {
            response = client.execute(httpPut);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != response){
                try {
                    response.close();
                    response=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != httpPut){
                httpPut.releaseConnection();
            }
            if(null != client){
                try {
                    client.close();
                    client = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

    private static void buildHeaders(Map<String, String> headers, HttpUriRequest http,boolean isValidate) {
        if(null != headers && !headers.isEmpty()){
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                http.addHeader(next.getKey(),next.getValue());
            }
        }
        if(isValidate){
            http.addHeader("appKey","86bfc555-c713-4162-964e-13c339acb35b");
            Long time = new Date().getTime();
            http.addHeader("time",time+"");
            String nonce = UUID.randomUUID().toString().replace("-", "").toUpperCase() + RandomStringUtils.randomAlphabetic(10)+System.currentTimeMillis();
            http.addHeader("nonce",nonce);
            http.addHeader("sign",CheckSumBuilder.getCheckSum("319e1e00-b913-44b3-ab9e-c6ddc00bb817",nonce,time+""));
        }
    }

    //post动作
    public static String sendPost(String url, Map<String,String> params, Map<String, String> headers,boolean isValidate){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        //设置请求头
        buildHeaders(headers, httpPost,isValidate);

        if(null!=params && params.size()>0) {
            //设置请求体
            List<NameValuePair> pairs = new ArrayList();
            Iterator<Map.Entry<String, String>> headersIterator = params.entrySet().iterator();
            while (headersIterator.hasNext()) {
                Map.Entry<String, String> map = headersIterator.next();
                String key = map.getKey();
                String value = map.getValue();
                pairs.add(new BasicNameValuePair(key, value));
            }
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(pairs, "utf-8");
                httpPost.setEntity(entity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != response) {
                    response.close();
                    response=null;
                }
                if(null != httpPost) {
                    httpPost.releaseConnection();
                }
                if(null != httpClient) {
                    httpClient.close();
                    httpClient=null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }



    //get动作
    public static String sendGet(String url,Map<String,String> params,Map<String,String> headers,boolean isValidate){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        if(null != params && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                pairs.add(new BasicNameValuePair(next.getKey(),next.getValue()));
            }
            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
                String s = EntityUtils.toString(urlEncodedFormEntity, "utf-8");
                url += "?"+s;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpGet httpGet = new HttpGet(url);
        buildHeaders(headers, httpGet,isValidate);
        CloseableHttpResponse response=null;
        String result="";
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != response){
                try {
                    response.close();
                    response=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != httpGet){
                httpGet.releaseConnection();
            }
            if(null != client){
                try {
                    client.close();
                    client=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return result;
    }
}
