package com.example.demo.rpcsimple.common;

import com.alibaba.fastjson.JSON;

/**
 * 定义消息输入输出格式，
 * 消息类型、消息唯一ID和消息的json序列化字符串内容。消息唯一ID是用来客户端验证服务器请求和响应是否匹配。
 */
public class MessageInput {
    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息唯一ID
     */
    private String requestId;

    /**
     * 消息的json序列化字符串内容
     */
    private String payload;

    public MessageInput() {
    }

    public MessageInput(String type, String requestId, String payload) {
        this.type = type;
        this.requestId = requestId;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public String getRequestId() {
        return requestId;
    }

    /**
     * 因为我们想直接拿到对象，所以要提供对象的类型参数
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getPayload(Class<T> clazz) {
        if (payload == null) {
            return null;
        }
        return JSON.parseObject(payload, clazz);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
