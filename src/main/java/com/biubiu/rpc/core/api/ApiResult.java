package com.biubiu.rpc.core.api;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author yule.zhang
 * @date 2019/2/22 23:17
 * @email zhangyule1993@sina.com
 * @description RPC返回消息
 */
public class ApiResult {

    //消息头
    private ApiResultHead head;

    //消息内容
    private Object body;

    /**
     * 构造方法
     */
    public ApiResult() {
        this.head = new ApiResultHead();
    }

    /**
     * 构造返回的消息
     * @param msg
     * @throws Exception
     */
    public ApiResult(String msg) throws Exception {

        //data解析成json对象
        JSONObject json = JSONObject.parseObject(msg);

        //判断消息是否完整
        //1、判断消息体的消息头是否为空
        if(!json.containsKey("head")){
            //如果消息头为空,重新构造消息头的状态为-1
            this.head.setStatus(-1);
            //抛出异常
            throw new Exception("request head is null");
        } else {
            //如果消息包含消息头,解析获得消息头
            JSONObject head = json.getJSONObject("head");

            //判断消息头是否包含状态字段
            if(head.containsKey("status")) {
                //如果包含状态字段,设置状态
                this.head.setStatus(head.getIntValue("status"));
            }

            //判断是否包含消息体
            if(json.containsKey("body")){
                this.setBody(json.getString("body"));
            }
        }
    }

    public ApiResultHead getHead() {
        return head;
    }

    public void setHead(ApiResultHead head) {
        this.head = head;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    /**
     * 作用：在json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响。
     * 使用方法：一般标记在属性或者方法上，返回的json数据即不包含该属性。
     * @return
     */
    @JsonIgnore
    public boolean isSuccess(){
        //状态在[200,300)区间,认为请求是成功的
        return this.head.getStatus() >= 200 && this.getHead().getStatus() < 300;
    }

    @Override
    public String toString(){
        return "status:" + this.getHead().getStatus();
    }
}
