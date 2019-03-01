package com.biubiu.rpc.core.rpc;

import com.biubiu.rpc.core.api.ApiResult;

public interface HttpCall {

    //get
    public ApiResult get() throws Exception;

    //post
    public ApiResult post() throws Exception;

    //post
    public ApiResult post(String variable) throws Exception;

    //put
    public ApiResult put() throws Exception;

    //put
    public ApiResult put(String variable) throws Exception;

    //delete
    public ApiResult delete() throws Exception;
}
