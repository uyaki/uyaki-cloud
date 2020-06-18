package com.uyaki.cloud.microservices.protocol.message;

import com.uyaki.cloud.microservices.protocol.message.command.Command;

/**
 * @author uyaki
 * @date 2019-10-15 14:51
 */
public class HeartbeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
