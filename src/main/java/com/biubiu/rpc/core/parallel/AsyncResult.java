package com.biubiu.rpc.core.parallel;

import com.biubiu.rpc.core.api.ApiResult;

/**
 * @author yule.zhang
 * @date 2019/2/23 15:58
 * @email zhangyule1993@sina.com
 * @description 异步结果
 */
public class AsyncResult {

    private ApiResult result;

    private Exception exception;

    public AsyncResult() {
    }

    public ApiResult getResult() {
        return result;
    }

    public void setResult(ApiResult result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * 判断是否成功
     * @return
     */
    public boolean isSuccess() {
        return this.result != null;
    }
}
