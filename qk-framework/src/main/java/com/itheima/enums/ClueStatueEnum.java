package com.itheima.enums;

import lombok.Getter;

@Getter
public enum ClueStatueEnum {

    ASSIGN(1, "待分配"),
    ASSIGNED(2, "待跟进"),
    FOLLOW_UP(3, "跟进中"),
    FAKE(4, "伪线索"),
    TRANSFORM(5, "转商机");

    ClueStatueEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;
}
