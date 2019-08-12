package com.gknoone.cloud.plus.common.core.enums;

/**
 * @author noone
 */

public enum LogEnum {
    /*
    日志枚举
     */
    BUSINESS("business"),
    PLATFORM("platform"),
    DB("db"),
    EXCEPTION("exception");

    private String category;

    LogEnum(String category) {
        this.category = category;
    }

    public String category() {
        return category;
    }

}
