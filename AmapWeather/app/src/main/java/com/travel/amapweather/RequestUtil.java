package com.travel.amapweather;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RequestUtil {
    private static final String TAG = "RequestUtil";
    private static final javax.net.ssl.HttpsURLConnection HttpsURLConnection = null;
    
    

    static {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
            public boolean verify(String string, SSLSession ssls) {
                return true;
            }
        });
    }


    /**
     */
    public static String request(String path, String method, String data)

            throws IOException {
        if(TextUtils.isEmpty(data)){
            data="";
        }
        URL url = null;
        if(method.equalsIgnoreCase("GET")){
            path+="?"+data;
        }
        url = new URL(path);


        Log.i(TAG, "??????    " + path + "\n??????" + method + "\n????????????" + data);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(20000);


        if(!method.equalsIgnoreCase("GET")){
            boolean isJson=false;
            try {
                new JSONObject(data);
                isJson=true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(isJson) {
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/json");

            }
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data.getBytes());

            outputStream.flush();
            outputStream.close();

        }else {
        }
        Log.i(TAG, "request: "+httpURLConnection.getResponseCode());
        BufferedInputStream bis = new BufferedInputStream(
                httpURLConnection.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while ((len = bis.read(arr)) != -1) {
            bos.write(arr, 0, len);
            bos.flush();
        }

        bos.close();

        httpURLConnection.disconnect();
        String readData = bos.toString("utf-8");
        Log.i(TAG, "????????????" + readData);
        return readData;
    }

    public static String requestHttps(String path, String method, String data)
            throws IOException {
        URL url = null;
        url = new URL(path);
        Log.i(TAG, "??????    " + path + "\n??????" + method + "\n????????????" + data);
        HttpsURLConnection httpURLConnection = (HttpsURLConnection) url
                .openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(10000);
        if(!TextUtils.isEmpty(data)){
            boolean isJson=false;
            try {
                JSONObject jsonObject=new JSONObject(data);
                isJson=true;
                jsonObject=null;
            } catch (JSONException e) {
            }
            if(!isJson) {
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
            }else {

                httpURLConnection.setRequestProperty("Content-Type",
                        "application/json");
            }
        }


        if(!method.equals("GET")){
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
        }

        if(!TextUtils.isEmpty(data)) {
            OutputStream outputStream = httpURLConnection.getOutputStream();

            outputStream.write(data.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }



        BufferedInputStream bis = new BufferedInputStream(
                httpURLConnection.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while ((len = bis.read(arr)) != -1) {
            bos.write(arr, 0, len);
            bos.flush();
        }

        bos.close();
        httpURLConnection.disconnect();
        String readData = bos.toString("utf-8");
        Log.i(TAG, "????????????" + readData);
        return readData;
    }
    public static String requestHttps2(String path, String method, String data)
            throws IOException {
        URL url = null;
        url = new URL(path);
        Log.i(TAG, "??????    " + path + "\n??????" + method + "\n????????????" + data);
        HttpsURLConnection httpURLConnection = (HttpsURLConnection) url
                .openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(10000);
        if(!TextUtils.isEmpty(data)){
            boolean isJson=false;
            try {
                JSONObject jsonObject=new JSONObject(data);
                isJson=true;
                jsonObject=null;
            } catch (JSONException e) {
            }
            if(!isJson) {
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
            }else {

                httpURLConnection.setRequestProperty("Content-Type",
                        "application/json");
            }
        }
//        if(DeviceHelper.getsCookies()!=null) {
//            String[] split = DeviceHelper.getsCookies().split("=");
//            Log.i(TAG, "requestHttps: --------------------"+DeviceHelper.getsCookies());
//            httpURLConnection.setRequestProperty("Cookie",DeviceHelper.getsCookies());
//
//        }


        if(!method.equals("GET")){
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
        }

        if(!TextUtils.isEmpty(data)) {
            OutputStream outputStream = httpURLConnection.getOutputStream();

            outputStream.write(data.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }


//        String headerField = httpURLConnection.getHeaderField("Set-Cookie");
//        if(!TextUtils.isEmpty(headerField)) {
//            DeviceHelper.setsCookies(headerField);
//            Log.i(TAG, "requestHttps: headerField = "+headerField);
//        }

        BufferedInputStream bis = new BufferedInputStream(
                httpURLConnection.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while ((len = bis.read(arr)) != -1) {
            bos.write(arr, 0, len);
            bos.flush();
        }
       
        bos.close();
        httpURLConnection.disconnect();
        String readData = bos.toString("utf-8");
        Log.i(TAG, "????????????" + readData);
        return readData;
    }
    
}
