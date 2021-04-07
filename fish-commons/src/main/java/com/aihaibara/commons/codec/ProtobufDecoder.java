package com.aihaibara.commons.codec;

import com.aihaibara.commons.ProtoInstant;
import com.aihaibara.commons.ProtoMsg;
import com.aihaibara.commons.exception.InvalidFrameException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器
 */
public class ProtobufDecoder  extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 标记一下当前的readIndex的位置
        byteBuf.markReaderIndex();
        // 判断包头长度
        if (byteBuf.readableBytes() < 8) {// 不够包头
            return;
        }
        //读取魔数
        short magic = byteBuf.readShort();
        if (magic != ProtoInstant.MAGIC_CODE) {
            String error = "客户端口令不对:" + channelHandlerContext.channel().remoteAddress();
            throw new InvalidFrameException(error);
        }
        //读取版本
        short version = byteBuf.readShort();
        // 读取传送过来的消息的长度。
        int length = byteBuf.readInt();

        // 长度如果小于0
        if (length < 0) {// 非法数据，关闭连接
            channelHandlerContext.close();
        }

        if (length > byteBuf.readableBytes()) {// 读到的消息体长度如果小于传送过来的消息长度
            // 重置读取位置
            byteBuf.resetReaderIndex();
            return;
        }


        byte[] array;
        if (byteBuf.hasArray()) {
            //堆缓冲
            ByteBuf slice = byteBuf.slice();
            array = slice.array();
        } else {
            //直接缓冲
            array = new byte[length];
            byteBuf.readBytes(array, 0, length);
        }

//        if(in.refCnt()>0)
//        {
////            log.debug("释放临时缓冲");
//            in.release();
//        }

        // 字节转成对象
        ProtoMsg.Message outmsg =
                ProtoMsg.Message.parseFrom(array);


        if (outmsg != null) {
            // 获取业务消息
            list.add(outmsg);
        }

    }
}
