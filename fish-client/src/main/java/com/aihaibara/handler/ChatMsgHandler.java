package com.aihaibara.handler;

import com.aihaibara.commons.ProtoMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Service;

@ChannelHandler.Sharable
@Service("ChatMsgHandler")
public class ChatMsgHandler  extends ChannelInboundHandlerAdapter {
    public ChatMsgHandler() {

    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(null ==msg || (msg instanceof ProtoMsg.Message)){
            super.channelRead(ctx, msg);
            return;
        }


        ProtoMsg.Message pm = (ProtoMsg.Message)msg;
        ProtoMsg.HeadType headType = pm.getType();
        //判断类型是不是聊天
        if(!headType.equals(ProtoMsg.HeadType.MESSAGE_REQUEST)){
            super.channelRead(ctx, msg);
            return;
        }
        ProtoMsg.MessageRequest messageRequest = pm.getMessageRequest();
        String content = messageRequest.getContent();
        String uid = messageRequest.getFrom();
        System.out.println("收到消息 from uid:"+uid+"->"+content);


    }
}
