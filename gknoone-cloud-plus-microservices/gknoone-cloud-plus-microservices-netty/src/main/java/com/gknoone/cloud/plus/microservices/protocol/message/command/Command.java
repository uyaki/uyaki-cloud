package com.gknoone.cloud.plus.microservices.protocol.message.command;

/**
 * @author gknoone
 * @date 2019-10-15 14:46
 */
public interface Command {
    Byte HEARTBEAT_REQUEST = 1;
    Byte HEARTBEAT_RESPONSE = 2;
}
