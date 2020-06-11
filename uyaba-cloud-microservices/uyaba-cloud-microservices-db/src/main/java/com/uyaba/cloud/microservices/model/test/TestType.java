package com.uyaba.cloud.microservices.model.test;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class TestType implements Serializable {
    private Long id;

    private UUID uid;

    private Object j;

    private Object jb;

    private String[] texts;

    private UUID[] uids;

    private Long[] longs;

    private String[][] textss;

    private static final long serialVersionUID = 1L;

}