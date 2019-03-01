package com.biubiu.rpc.core.base;

import com.biubiu.rpc.util.StringEx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author yule.zhang
 * @date 2019/2/23 0:40
 * @email zhangyule1993@sina.com
 * @description Http连接
 */
public class BaseHttpConnection implements AutoCloseable {

    public static final int HTTP_CONNECTION_TIMEOUT = 6000;
    public static final int HTTP_READ_TIMEOUT = 30000;
    public static final int HTTP_READ_BUFFER_SIZE = 2048;

    //用来告诉服务端消息主体是序列化后的 JSON 字符串
    private static final String HTTP_POST_CONTENT_TYPE = "application/json";
    //这应该是最常见的 POST 提交数据的方式了。
    // 浏览器的原生 form 表单，如果不设置 enctype 属性，
    // 那么最终就会以 application/x-www-form-urlencoded 方式提交数据。
    // 请求的时候，首先，Content-Type 被指定为 application/x-www-form-urlencoded；
    // 其次，提交的数据按照 key1=val1&key2=val2 的方式进行编码，key 和 val 都进行了 URL 转码。
    // 大部分服务端语言都对这种方式有很好的支持。
    private static final String HTTP_POST_FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private HttpURLConnection connection;
    private String requestUrl = null;


    /**
     * 构造方法
     * @param requestUrl
     */
    public BaseHttpConnection(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * 重写requestUrl 的 get 方法
     * @return
     */
    public final String getRequestUrl() {
        return requestUrl;
    }

    /**
     * 重写 toString() 方法
     * @return
     */
    @Override
    public String toString() {
        return (this.connection == null ? "" : this.connection.getRequestMethod()) + " : " + this.requestUrl;
    }



    /**
     *
     * 获取HTTP连接
     * @param requestMethod  请求方式,GET,PUT,POST,DELETE ...
     * @param path         请求链接url
     * @param contentType  消息主体类型
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getConnection(String requestMethod,String path,String contentType) throws IOException {

        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT);
        connection.setReadTimeout(HTTP_READ_TIMEOUT);
        //是否连接遵循重定向
        //设置为true，则系统自动处理跳转，但是对于有多次跳转的情况，就只能处理第一次。
        //如果设置为false，则是你自己手动处理跳转，此时可以拿到一些比较有用的数据，比如cookie，Location之类的。
        connection.setInstanceFollowRedirects(true);
        //post 请求不能使用缓存
        connection.setUseCaches(false);
        // 设置是否向connection输出，
        // 如果这个是post请求，参数要放在http正文内，因此需要设为true
        connection.setDoOutput(true);
        //设置连接的请求类型
        connection.setRequestMethod(requestMethod);
        //配置本次连接的Content-type
        connection.setRequestProperty("Content-Type", StringEx.stringIsNullOrEmpty(contentType) ? HTTP_POST_CONTENT_TYPE : contentType);
        connection.setRequestProperty("Connection","Keep-Alive");
        connection.setRequestProperty("Charset","UTF-8");
        return connection;
    }


    /**
     * 获取请求响应的数据
     * @return
     * @throws IOException
     */
    public final String getString() throws IOException {
        InputStream inStream = this.connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream),HTTP_READ_BUFFER_SIZE);
        StringBuilder response = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            response.append(line);
        }
        return response.toString();
    }


    /**
     *
     * @param method  请求类型
     * @param data    请求参数
     * @param isForm  是否是form表单提交
     * @return
     * @throws IOException
     */
    public int request(String method, String data, boolean isForm) throws IOException {
        this.connection = getConnection(method, this.getRequestUrl(), isForm ? HTTP_POST_FORM_CONTENT_TYPE : HTTP_POST_CONTENT_TYPE);
        if(!StringEx.stringIsNullOrEmpty(data)){
            DataOutputStream outputStream = new DataOutputStream(this.connection.getOutputStream());
            outputStream.write(data.getBytes());
            outputStream.flush();
        }
        return this.connection.getResponseCode();
    }


    /**
     * 关闭连接
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if(this.connection != null){
            this.connection.disconnect();
        }
    }


}
