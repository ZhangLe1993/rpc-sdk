package com.biubiu.rpc.core.base;

import com.biubiu.rpc.core.api.ApiResult;
import com.biubiu.rpc.core.common.Handler;
import com.biubiu.rpc.core.rpc.HttpCall;
import com.biubiu.rpc.util.StringEx;
import com.biubiu.rpc.util.Web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yule.zhang
 * @date 2019/2/23 0:01
 * @email zhangyule1993@sina.com
 * @description RPC抽象类实现HttpHandler接口
 */
public abstract class BaseRpc implements HttpCall {

    private static String apiGateway;
    private static final Logger logger = LoggerFactory.getLogger(BaseRpc.class);
    protected static final String METHOD_GET = "GET";
    protected static final String METHOD_POST = "POST";
    protected static final String METHOD_PUT = "PUT";
    protected static final String METHOD_DELETE = "DELETE";

    //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    protected static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    protected BaseHttpConnection connection;

    public BaseRpc() {
    }


    /**
     * 根据规则
     * 解析构造url
     * @param url
     * @return
     */
    protected static final String constructURL(String url) throws UnsupportedEncodingException {
        if(StringEx.stringIsNullOrEmpty(url)){
            return null;
        } else {
            StringBuilder sb = new StringBuilder(StringEx.removeStartChar(url,"/"));
            if(url.contains("clt_id")){
                String cltId = Web.getRequestParameter("clt_id");
                sb.append(url.contains("?") ? "&" : "?");
                if(cltId != null){
                    sb.append("clt_id=" + cltId);
                } else {
                    sb.append("clt_id=null");
                }
            }
            //如果url中不是以http://或者https://开头，则返回的url为apiGateway + sb.toString(),
            //否则返回构造好的sb.toString()
            return !url.startsWith("http://") && !url.startsWith("https://") ? apiGateway + sb.toString() : sb.toString();
        }
    }

    /**
     * 封装消息
     * @param connection
     * @return
     * @throws Exception
     */
    protected static ApiResult constructResult(BaseHttpConnection connection) throws Exception {
        String data = connection.getString();
        return new ApiResult(data);
    }

    /**
     * 返回请求结果
     * @param url
     * @param method
     * @param data
     * @param isForm
     * @return
     * @throws Exception
     */
    protected static ApiResult requestResult(String url,String method,String data,boolean isForm) throws Exception {
        logger.info("url={},method={},data={},isForm={}", url, method, data, isForm);
        try{
            BaseHttpConnection connection = new BaseHttpConnection(url);
            Throwable var0 = null;
            ApiResult result = null;
            try{
                //执行获取response
                int code = connection.request(method,data,isForm);
                requestError(code);
                result = constructResult(connection);
            } catch(Throwable var1) {
                var0 = var1;
                throw var1;
            } finally {
                if(connection != null){
                    if(var0 != null) {
                        try{
                            connection.close();
                        } catch (Throwable var2) {
                            var0.addSuppressed(var2);
                        }
                    } else {
                        connection.close();
                    }
                }
            }
            return result;
        }catch(Exception e){
            throw e;
        }
    }


    /**
     * rpc异步方法
     * @param url
     * @param method
     * @param data
     * @param isForm
     * @param async
     * @throws Exception
     */
    protected static void requestResultAsync(String url,String method,String data,boolean isForm,Handler<Boolean,ApiResult> async) throws Exception {
        BaseHttpConnection connection = new BaseHttpConnection(url);
        cachedThreadPool.execute(() -> {
            //boolean flag = false;
            try{
                int code = connection.request(method,data,isForm);
                requestError(code);
                ApiResult result = constructResult(connection);
                connection.close();
                async.handler(result);
            }catch(Exception e){
                logger.error("rpc async error:", e);
            }
        });
    }

    protected static final void requestError(int code){
        if(code >= 200 && code > 300) {
            ;
        }
    }

    public static void setApiGatewayPath(String path) {
        path = path.trim();
        if (!path.endsWith("/")) {
            path = path + "/";
        }

        apiGateway = path;
    }

    //初始化微服务网关
    static {
        if(StringEx.stringIsNullOrEmpty(apiGateway)){
            apiGateway = "http://127.0.0.1/";
        }
    }
}
