package com.gknoone.cloud.plus.microservices.protocol.message.encoder;

import com.gknoone.cloud.plus.microservices.protocol.message.HeartbeatRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器
 * @author gknoone
 * @date 2019-10-15 14:49
 */
public class HeartbeatEncoder  extends MessageToByteEncoder<HeartbeatRequestPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HeartbeatRequestPacket msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getVersion());
        out.writeByte(msg.getCommand());
    }
}
