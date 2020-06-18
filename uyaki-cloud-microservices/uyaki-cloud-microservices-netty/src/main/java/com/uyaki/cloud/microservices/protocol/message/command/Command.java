package com.uyaki.cloud.microservices.protocol.message.command;

/**
 * @author uyaki
 * @date 2019-10-15 14:46
 */
public interface Command {
    Byte HEARTBEAT_REQUEST = 1;
    Byte HEARTBEAT_RESPONSE = 2;
}
