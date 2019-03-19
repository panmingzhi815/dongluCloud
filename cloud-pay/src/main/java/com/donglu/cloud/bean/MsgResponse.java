package com.donglu.cloud.bean;

import lombok.Data;

/**
 * @author panmingzhi815
 * 消息响应格式
 */
@Data
public class MsgResponse {
    private String code;
    private String msg;
    private boolean success;
    private Object data;
}
