package com.uyaba.cloud.microservices.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author uyaba
 * @date 2019-08-23 10:48
 */
@Data
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;

}
