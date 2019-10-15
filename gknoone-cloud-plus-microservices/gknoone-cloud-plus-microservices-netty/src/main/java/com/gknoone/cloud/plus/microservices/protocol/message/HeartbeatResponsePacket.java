package com.gknoone.cloud.plus.microservices.protocol.message;

import com.gknoone.cloud.plus.microservices.protocol.message.command.Command;

/**
 * @author gknoone
 * @date 2019-10-15 14:51
 */
public class HeartbeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
