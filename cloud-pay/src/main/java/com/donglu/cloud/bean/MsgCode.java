package com.donglu.cloud.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MsgCode {
    code_10001(10001,"id不存在"),
    ;
    private Integer code;
    private String msg;

}
