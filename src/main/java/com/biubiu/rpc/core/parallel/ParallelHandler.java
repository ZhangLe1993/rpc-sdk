package com.biubiu.rpc.core.parallel;

import com.biubiu.rpc.core.api.ApiResult;
import com.biubiu.rpc.core.rpc.HttpCall;

/**
 * @author yule.zhang
 * @date 2019/2/23 16:02
 * @email zhangyule1993@sina.com
 * @description 并发处理器接口
 */
public interface ParallelHandler {

    ApiResult handle(HttpCall var0) throws Exception;
}
