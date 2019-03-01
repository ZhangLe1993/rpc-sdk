package com.biubiu.rpc.core.rpc;

import com.biubiu.rpc.core.api.ApiResult;
import com.biubiu.rpc.core.base.BaseRpc;
import com.biubiu.rpc.core.common.Handler;
import com.biubiu.rpc.core.parallel.AsyncCallBack;
import com.biubiu.rpc.core.parallel.AsyncResult;
import com.biubiu.rpc.core.parallel.ExecuteWrapper;
import com.biubiu.rpc.core.parallel.RpcParallel;
import com.biubiu.rpc.util.StringEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yule.zhang
 * @date 2019/2/23 15:36
 * @email zhangyule1993@sina.com
 * @description HttpRpc
 */
public class HttpRpc extends BaseRpc {

    private static final Logger logger = LoggerFactory.getLogger(HttpRpc.class);
    private StringBuilder baseUrl;
    private boolean hasUrlParams = false;
    private List<String> params = new LinkedList<>();
    private String charEncoding = "UTF-8";

    private final String jsonData() {
        return StringEx.stringJoin(params,"&");
    }

    /**
     * 构造方法构造url
     * @param url
     */
    public HttpRpc(String url) {
        try{
            constructURL(url);
        }catch(UnsupportedEncodingException e){
            logger.error("HttpRpc url construct error ", e);
        }
        this.hasUrlParams = this.params.contains("?");
        this.baseUrl = new StringBuilder(url);
    }

    public final HttpRpc addUrl(String name, String data){
        this.baseUrl.append(this.hasUrlParams ? "&" : "?");
        try{
            this.baseUrl.append(URLEncoder.encode(name,charEncoding));
            this.baseUrl.append("=");
            this.baseUrl.append(URLEncoder.encode(data,charEncoding));
        }catch (UnsupportedEncodingException e){
            logger.error("addUrl error :", e);
        }
        this.hasUrlParams = true;
        return this;
    }

    public final HttpRpc addForm(String name, Object data) {
        try{
            if(data == null){
                data = "";
            }
            this.params.add(URLEncoder.encode(name,charEncoding) + "=" + URLEncoder.encode(data.toString(),charEncoding));
        }catch(UnsupportedEncodingException e){
            logger.error("addForm error :", e);
        }
        return this;
    }

    public AsyncResult asParallel(RpcParallel parallel, Handler<ApiResult,HttpCall> call) {
        ExecuteWrapper executeWrapper = new ExecuteWrapper(new AsyncResult(),call,this);
        parallel.add(executeWrapper);
        return executeWrapper.getResult();
    }

    @Override
    public ApiResult get() throws Exception {
        return requestResult(this.baseUrl.toString(),"GET",null,false);
    }

    @Override
    public ApiResult post() throws Exception {
        return requestResult(this.baseUrl.toString(),"POST", this.jsonData(), true);
    }

    @Override
    public ApiResult post(String variable) throws Exception {
        return requestResult(this.baseUrl.toString(),"POST", variable, !StringEx.isJson(variable));
    }

    @Override
    public ApiResult put() throws Exception {
        return requestResult(this.baseUrl.toString(),"PUT",this.jsonData(),true);
    }

    @Override
    public ApiResult put(String variable) throws Exception {
        return requestResult(this.baseUrl.toString(),"PUT",variable,!StringEx.isJson(variable));
    }

    @Override
    public ApiResult delete() throws Exception {
        return requestResult(this.baseUrl.toString(),"DELETE",null,false);
    }

    public void getAsync(AsyncCallBack call) throws Exception {
        requestResultAsync(this.baseUrl.toString(),"GET",null,false, call);
    }

    public void postAsync(String json, AsyncCallBack call) throws Exception {
        requestResultAsync(this.baseUrl.toString(),"POST",json,!StringEx.isJson(json),call);
    }

    public void putAsync(String json, AsyncCallBack call) throws Exception {
        requestResultAsync(this.baseUrl.toString(),"PUT",json,!StringEx.isJson(json),call);
    }

    public void deleteAsync(AsyncCallBack call) throws Exception {
        requestResultAsync(this.baseUrl.toString(),"DELETE",null,false,call);
    }
}
