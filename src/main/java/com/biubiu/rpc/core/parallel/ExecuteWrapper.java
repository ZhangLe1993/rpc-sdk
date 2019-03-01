package com.biubiu.rpc.core.parallel;

import com.biubiu.rpc.core.api.ApiResult;
import com.biubiu.rpc.core.common.Handler;
import com.biubiu.rpc.core.rpc.HttpCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask代表有返回值的任务
 * RecursiveAction代表没有返回值的任务。
 * @author yule.zhang
 * @date 2019/2/23 16:01
 * @email zhangyule1993@sina.com
 * @description a
 */
public class ExecuteWrapper extends RecursiveTask<ApiResult> {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteWrapper.class);
    private final AsyncResult result;
    private final Handler<ApiResult,HttpCall> call;
    private final HttpCall handler;

    public ExecuteWrapper(AsyncResult result, Handler<ApiResult, HttpCall> call, HttpCall handler) {
        this.result = result;
        this.call = call;
        this.handler = handler;
    }

    @Override
    protected ApiResult compute() {
        try{
            ApiResult result = this.call.handler(this.handler);
            this.result.setResult(result);
            return result;
        }catch(Exception e){
            logger.error("ExecuteWrapper compute error:", e);
            this.result.setException(e);
            return null;
        }
    }

    public AsyncResult getResult(){
        return this.result;
    }
}
