package com.biubiu.rpc.core.api;

import java.util.List;

/**
 * @author yule.zhang
 * @date 2019/2/22 23:15
 * @email zhangyule1993@sina.com
 * @description RPC返回消息头
 */
public class ApiResultHead {

    private int status;

    private List<Object> debug;

    public ApiResultHead() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Object> getDebug() {
        return debug;
    }

    public void setDebug(List<Object> debug) {
        this.debug = debug;
    }
}
