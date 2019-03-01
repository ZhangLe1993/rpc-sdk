package com.biubiu.rpc.core.parallel;

import com.biubiu.rpc.core.api.ApiResult;
import com.biubiu.rpc.core.common.Handler;

/**
 * @author yule.zhang
 * @date 2019/2/23 15:58
 * @email zhangyule1993@sina.com
 * @description 异步调用结果
 */
public interface AsyncCallBack extends Handler<Boolean,ApiResult> {

    @Override
    Boolean handler(ApiResult apiResult) throws Exception;
}
