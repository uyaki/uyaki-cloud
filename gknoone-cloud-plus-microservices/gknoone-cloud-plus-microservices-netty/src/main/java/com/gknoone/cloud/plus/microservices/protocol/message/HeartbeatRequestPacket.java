package com.gknoone.cloud.plus.microservices.protocol.message;

import static com.gknoone.cloud.plus.microservices.protocol.message.command.Command.HEARTBEAT_REQUEST;

/**
 * @author gknoone
 * @date 2019-10-15 14:51
 */
public class HeartbeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
