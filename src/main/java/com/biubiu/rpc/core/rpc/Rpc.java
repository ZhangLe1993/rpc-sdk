package com.biubiu.rpc.core.rpc;

import com.biubiu.rpc.core.api.ApiResult;
import com.biubiu.rpc.core.base.BaseRpc;
import com.biubiu.rpc.core.parallel.RpcParallel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yule.zhang
 * @date 2019/2/23 18:04
 * @email zhangyule1993@sina.com
 * @description RPC工具类
 */
public class Rpc {
    private static final Logger logger = LoggerFactory.getLogger(Rpc.class);

    public Rpc() {
    }

    public static final HttpRpc HttpCall(String url) {
        return new HttpRpc(url);
    }

    public static final RpcParallel parallel(){
        return parallel();
    }

    public static ApiResult get(String url) throws Exception {
        return HttpCall(url).get();
    }

    public static ApiResult post(String url,String json) throws Exception {
        return HttpCall(url).post(json);
    }

    public static ApiResult put(String url,String json) throws Exception {
        return HttpCall(url).put(json);
    }

    public static ApiResult delete(String url) throws Exception {
        return HttpCall(url).delete();
    }

    public static void setApiGateway(String apiGateway){
        BaseRpc.setApiGatewayPath(apiGateway);
    }
}
