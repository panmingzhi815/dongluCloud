package com.donglu.cloud.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MsgCode {
    code_10001(10001, "id不存在"),
    code_10002(10002, "文件不存在"),
    code_10003(10003, "验证失败"),
    ;
    private Integer code;
    private String msg;

}
