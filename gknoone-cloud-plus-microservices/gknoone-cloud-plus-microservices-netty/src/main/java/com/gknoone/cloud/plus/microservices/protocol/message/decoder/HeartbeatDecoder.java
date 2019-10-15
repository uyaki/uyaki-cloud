package com.gknoone.cloud.plus.microservices.protocol.message.decoder;

import com.gknoone.cloud.plus.microservices.protocol.message.HeartbeatRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gknoone
 * @date 2019-10-15 14:47
 */
@Slf4j
public class HeartbeatDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte version = in.readByte();
        byte command = in.readByte();
        log.info("version : {}, command : {}", version, command);
        HeartbeatRequestPacket requestPacket = new HeartbeatRequestPacket();
        requestPacket.setVersion(version);
        out.add(requestPacket);
    }
}
