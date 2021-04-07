package com.aihaibara.server.hander;

import com.aihaibara.server.processer.LoginProcesser;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginRequestHandler  extends ChannelInboundHandlerAdapter {

    @Autowired
    LoginProcesser loginProcesser;
}
