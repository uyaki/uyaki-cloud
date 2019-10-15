package com.gknoone.cloud.plus.microservices.protocol.message;

import lombok.Data;

/**
 * @author gknoone
 * @date 2019-10-15 14:50
 */
@Data
public abstract class Packet {
    /**
     * 版本
     */
    private Byte version = 1;

    public abstract Byte getCommand();
}
