package com.aihaibara.handler;


import com.aihaibara.client.ClientSession;
import com.aihaibara.commons.ProtoInstant;
import com.aihaibara.commons.ProtoMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@ChannelHandler.Sharable
@Service("LoginResponceHandler")
public class LoginResponceHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg == null || !(msg instanceof ProtoMsg.Message)){
            super.channelRead(ctx, msg);
            return;
        }
        ProtoMsg.Message pm = (ProtoMsg.Message)msg;
        ProtoMsg.HeadType type = pm.getType();
        //判断类型是否为登录
        if(!type.equals(ProtoMsg.HeadType.LOGIN_RESPONSE)){
            super.channelRead(ctx, msg);
            return;
        }
        //判断返回是否成功
        ProtoMsg.LoginResponse loginResponse = pm.getLoginResponse();

        ProtoInstant.ResultCodeEnum result =
                ProtoInstant.ResultCodeEnum.values()[loginResponse.getCode()];

        if (!result.equals(ProtoInstant.ResultCodeEnum.SUCCESS)) {
            //登录失败
            log.info(result.getDesc());
        } else {
            //登录成功
            ClientSession.loginSuccess(ctx, pm);
            ChannelPipeline p = ctx.pipeline();
            //移除登录响应处理器
            p.remove(this);

            //在编码器后面，动态插入心跳处理器
            p.addAfter("encoder", "heartbeat", new HeartBeatClientHandler());
        }


    }
}
