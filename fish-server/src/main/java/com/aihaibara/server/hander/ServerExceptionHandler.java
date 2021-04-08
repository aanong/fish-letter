package com.aihaibara.server.hander;

import com.aihaibara.commons.exception.InvalidFrameException;
import com.aihaibara.server.server.ServerSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@ChannelHandler.Sharable
@Service("ServerExceptionHandler")
@Slf4j
public class ServerExceptionHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // ..

        if (cause instanceof InvalidFrameException) {
            log.error(cause.getMessage());
            ServerSession.closeSession(ctx);
        } else {

            //捕捉异常信息
//            cause.printStackTrace();
            log.error(cause.getMessage());
            ctx.close();
        }
    }

    /**
     * 通道 Read 读取 Complete 完成
     * 做刷新操作 ctx.flush()
     */
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
            throws Exception {
        ServerSession.closeSession(ctx);
    }
}
