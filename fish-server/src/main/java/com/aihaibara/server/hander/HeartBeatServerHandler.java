package com.aihaibara.server.hander;

import com.aihaibara.cocurrent.FutureTaskScheduler;
import com.aihaibara.commons.ProtoMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HeartBeatServerHandler extends IdleStateHandler {
    private static final int READ_IDLE_GAP = 150;

    public HeartBeatServerHandler() {
        super(READ_IDLE_GAP, 0, 0, TimeUnit.SECONDS);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //判断消息是否为空或者msg是否正确
        if(msg==null||msg instanceof ProtoMsg.Message){
            super.channelRead(ctx, msg);
            return;
        }
        ProtoMsg.Message pm = (ProtoMsg.Message)msg;
        ProtoMsg.HeadType headType = pm.getType();
        if(headType.equals(ProtoMsg.HeadType.HEATBEAT_REQUEST)){
            //异步处理,将心跳包，直接回复给客户端
            FutureTaskScheduler.add(() -> {
                if (ctx.channel().isActive()) {
                    ctx.writeAndFlush(msg);
                }
            });
            super.channelRead(ctx, msg);

        }

    }
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READ_IDLE_GAP + "秒内未读到数据，关闭连接");
       // ServerSession.closeSession(ctx);
    }
}
