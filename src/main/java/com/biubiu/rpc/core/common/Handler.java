package com.biubiu.rpc.core.common;

/**
 * @author yule.zhang
 * @date 2019/2/23 13:42
 * @email zhangyule1993@sina.com
 * @description 异步回调接口
 */
public interface Handler<Result,Param> {
    Result handler(Param param) throws Exception;
}
