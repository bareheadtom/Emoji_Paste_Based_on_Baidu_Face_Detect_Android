package utils;
/*package com.baidu.ai.aip.utils;*/

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.muzhi.camerasdk.example.MainActivity1;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import balloonrelativelayout.MainActivity;

/**
 * http 工具类
 */
public class HttpUtil {

    public static String post(String requestUrl, String accessToken, String params)
            throws Exception {
        String contentType = "application/x-www-form-urlencoded";
        return HttpUtil.post(requestUrl, accessToken, contentType, params);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params)
            throws Exception {
        String encoding = "UTF-8";
        if (requestUrl.contains("nlp")) {
            encoding = "GBK";
        }
        return HttpUtil.post(requestUrl, accessToken, contentType, params, encoding);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params, String encoding)
            throws Exception {
        String url = requestUrl + "?access_token=" + accessToken;
        return HttpUtil.postGeneralUrl(url, contentType, params, encoding);
    }

    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
            throws Exception {
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(params.getBytes(encoding));
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encoding));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        System.out.println("result:" + result);
        return result;
    }
    public static String getJsonUrl(String Url){
        String result=null;
        try {
            URL url = new URL(Url);
            //URL url = new URL(URLEncoder.encode(path));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
             result = "";
            String getLine;
            while ((getLine = br.readLine()) != null) {
                result += getLine;
            }
            is.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("jsonResult:"+result);
        return result;
    }
    public static byte[] getPicUrl(String Url){
        byte[] data=null;
        try {
            URL url = new URL(Url);
            //URL url = new URL(URLEncoder.encode(path));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            int len=0;
            byte[] buffer=new byte[2048];
            while ((len=is.read(buffer))!=-1) {
                outputStream.write(buffer,0,len);
            }
            is.close();
            data=outputStream.toByteArray();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String postGeneralUrlPic(String generalUrl, String contentType, byte[] params, String encoding)
            throws Exception {
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(params);
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encoding));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        System.err.println("result:" + result);
        return result;
    }
    public static String uploadFile(Bitmap bitmap, String requestURL,String imageName)
    {
        String TAG = "uploadFile";
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String BOUNDARY =  UUID.randomUUID().toString();  //随机生成边界

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30 * 1000); //30秒连接超时
            connection.setReadTimeout(30 * 1000);   //30秒读取超时
            connection.setDoInput(true);  //允许文件输入流
            connection.setDoOutput(true); //允许文件输出流
            connection.setUseCaches(false);  //不允许使用缓存
            connection.setRequestMethod("POST");  //请求方式为POST
            connection.setRequestProperty("Charset", "utf-8");  //设置编码为utf-8
            connection.setRequestProperty("connection", "keep-alive"); //保持连接
            //connection.setRequestProperty("Cookie", "sid=" + firstCookie + ";" + "cgi_ck=" +secondCookie);//设置cookie，多个cookie用;分开
            connection.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + BOUNDARY); //特别注意：Content-Type必须为multipart/form-data

            //如果传入的文件路径不为空的话，则读取文件并上传
                //读取图片进行压缩
                //如果不需要压缩的话直接读取文件则可 InputStream is = new FileInputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //0-100 100为不压缩
                InputStream is = new ByteArrayInputStream(baos.toByteArray());

                OutputStream outputSteam = connection.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);

                //特别注意
                //name是服务器端需要key;filename是文件的名字（包括后缀）
                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+imageName+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+"UTF-8"+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());

                byte[] bytes = new byte[1024];
                int len = 0;
                while((len=is.read(bytes))!=-1)
                {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();

                //获取返回码，根据返回码做相应处理
                int res = connection.getResponseCode();
               // Log.d(TAG, "response code:"+res);
                if(connection.getResponseCode() == 200)
                {
                    BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = input.readLine()) != null)
                    {
                        result.append(line).append("\n");
                    }
                   // Log.i(TAG, result.toString());
                    //返回图片名称
                    return result.toString();
                }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
