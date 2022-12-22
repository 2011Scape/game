package gg.rsmod.net.codec.login

import gg.rsmod.net.codec.StatefulFrameDecoder
import gg.rsmod.util.io.BufferUtils.readString
import gg.rsmod.util.io.Xtea
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import mu.KLogging
import java.math.BigInteger

/**
 * @author Tom <rspsmods@gmail.com>
 */
class LoginDecoder(
    private val serverRevision: Int, private val cacheCrcs: IntArray,
    private val serverSeed: Long, private val rsaExponent: BigInteger?, private val rsaModulus: BigInteger?
) : StatefulFrameDecoder<LoginDecoderState>(LoginDecoderState.HANDSHAKE) {

    private var payloadLength = -1

    private var reconnecting = false

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>, state: LoginDecoderState) {
        buf.markReaderIndex()
        when (state) {
            LoginDecoderState.HANDSHAKE -> decodeHandshake(ctx, buf)
            LoginDecoderState.HEADER -> decodeHeader(ctx, buf, out)
        }
    }

    private fun decodeHandshake(ctx: ChannelHandlerContext, buf: ByteBuf) {
        if (buf.isReadable) {
            val opcode = buf.readByte().toInt()
            if (opcode == LOGIN_OPCODE || opcode == RECONNECT_OPCODE) {
                reconnecting = opcode == RECONNECT_OPCODE
                setState(LoginDecoderState.HEADER)
            } else {
                ctx.writeResponse(LoginResultType.BAD_SESSION_ID)
            }
        }
    }

    private fun decodeHeader(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        if (buf.readableBytes() >= 3) {
            val size = buf.readUnsignedShort()
            if (buf.readableBytes() >= size) {
                val revision = buf.readInt()
                if (revision == serverRevision) {
                    decodePayload(ctx, buf, out)
                } else {
                    ctx.writeResponse(LoginResultType.REVISION_MISMATCH)
                }
            } else {
                buf.resetReaderIndex()
            }
        }
    }

    private fun decodePayload(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        buf.markReaderIndex()

        buf.readUnsignedByte()

        val secureBuf: ByteBuf = if (rsaExponent != null && rsaModulus != null) {
            val secureBufLength = buf.readUnsignedShort()
            val secureBuf = buf.readBytes(secureBufLength)
            val rsaValue = BigInteger(secureBuf.array()).modPow(rsaExponent, rsaModulus)
            Unpooled.wrappedBuffer(rsaValue.toByteArray())
        } else {
            buf
        }

        val successfulEncryption = secureBuf.readUnsignedByte().toInt() == 10
        if (!successfulEncryption) {
            buf.resetReaderIndex()
            logger.info("Channel '{}' login request rejected.", ctx.channel())
            ctx.writeResponse(LoginResultType.BAD_SESSION_ID)
            return
        }

        val xteaKeys = IntArray(4)
        for (i in xteaKeys.indices) {
            xteaKeys[i] = secureBuf.readInt()
        }

        val password: String?
        val previousXteaKeys = IntArray(4)

        if (reconnecting) {
            for (i in previousXteaKeys.indices) {
                previousXteaKeys[i] = secureBuf.readInt()
            }
            password = null
        } else {
            password = secureBuf.readString()
        }

        val reportedSeed = secureBuf.readLong()
        val seedServer = secureBuf.readLong()
        secureBuf.clear()
        val xteaBuf = buf.decipher(xteaKeys)
        val username = xteaBuf.readString()

        val unknown = xteaBuf.readByte()
        val clientSettings = xteaBuf.readByte().toInt()
        val clientResizable = (clientSettings shr 1) == 1
        val clientWidth = xteaBuf.readUnsignedShort()
        val clientHeight = xteaBuf.readUnsignedShort()
        logger.info { "User '$username' login request from ${ctx.channel()}." }

        val request = LoginRequest(
            channel = ctx.channel(),
            username = username,
            password = password ?: "",
            revision = serverRevision,
            xteaKeys = xteaKeys,
            resizableClient = clientResizable,
            auth = -1,
            uuid = "".toUpperCase(),
            clientWidth = clientWidth,
            clientHeight = clientHeight,
            reconnecting = reconnecting
        )
        out.add(request)
    }

    private fun ChannelHandlerContext.writeResponse(result: LoginResultType) {
        val buf = channel().alloc().buffer(1)
        buf.writeByte(result.id)
        writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE)
    }

    private fun ByteBuf.decipher(xteaKeys: IntArray): ByteBuf {
        val data = ByteArray(readableBytes())
        readBytes(data)
        return Unpooled.wrappedBuffer(Xtea.decipher(xteaKeys, data, 0, data.size))
    }

    companion object : KLogging() {
        private const val LOGIN_OPCODE = 16
        private const val RECONNECT_OPCODE = 18
    }
}
