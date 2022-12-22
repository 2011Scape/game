package gg.rsmod.net.codec.login

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

/**
 * @author Tom <rspsmods@gmail.com>
 */
class LoginEncoder : MessageToByteEncoder<LoginResponse>() {

    override fun encode(ctx: ChannelHandlerContext, msg: LoginResponse, out: ByteBuf) {
        out.writeByte(msg.result.id)
        out.writeByte(14)
        out.writeByte(msg.privilege)
        out.writeByte(0)
        out.writeByte(0)
        out.writeByte(0)
        out.writeByte(1)
        out.writeByte(0)
        out.writeShort(msg.index)
        out.writeByte(1)
        out.writeMedium(0)
        out.writeByte(1)
        out.writeBytes("".toByteArray(Charsets.UTF_8))
        out.writeByte(0)
    }
}
