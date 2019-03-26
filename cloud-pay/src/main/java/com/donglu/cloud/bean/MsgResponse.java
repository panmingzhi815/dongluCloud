package com.donglu.cloud.bean;

import lombok.Data;

/**
 * @author panmingzhi815
 * 消息响应格式
 */
@Data
public class MsgResponse {
    private Integer code;
    private String msg;
    private Long count;
    private Boolean success;
    private Object data;

    public static MsgResponse success(Integer code, String msg, Long count, Object data) {
        MsgResponse msgResponse = new MsgResponse();
        msgResponse.setCode(code);
        msgResponse.setMsg(msg);
        msgResponse.setCount(count);
        msgResponse.setSuccess(true);
        msgResponse.setData(data);
        return msgResponse;
    }

    public static MsgResponse success(Integer code, String msg) {
        return success(code, msg, null, null);
    }

    public static MsgResponse fail(MsgCode msgCode) {
        MsgResponse msgResponse = new MsgResponse();
        msgResponse.setCode(msgCode.getCode());
        msgResponse.setMsg(msgCode.getMsg());
        msgResponse.setSuccess(false);
        return msgResponse;
    }

}
