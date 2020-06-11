package com.uyaba.cloud.common.core.enums;

public enum  RegularEnums {
    //后缀正则表达式
    FILE_SUFFIX("\\.(.+)$");
    private String value;

    RegularEnums(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
