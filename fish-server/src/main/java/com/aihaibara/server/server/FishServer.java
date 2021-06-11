package com.aihaibara.server.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service("ChatServer")
public class FishServer {

    // 服务器端口
    @Value("${fish.server.port}")
    private int port;

    //
    private EventLoopGroup bg =
            new NioEventLoopGroup();
    private EventLoopGroup wg =
            new NioEventLoopGroup();


    //启动引导类
    private ServerBootstrap b =
            new ServerBootstrap();

    //启动
    public void run(){

    }
}
