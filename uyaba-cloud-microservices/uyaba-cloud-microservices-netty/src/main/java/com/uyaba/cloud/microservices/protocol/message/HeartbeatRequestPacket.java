package com.uyaba.cloud.microservices.protocol.message;

import com.uyaba.cloud.microservices.protocol.message.command.Command;

/**
 * @author uyaba
 * @date 2019-10-15 14:51
 */
public class HeartbeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
