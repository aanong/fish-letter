package com.aihaibara.server.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

@Data
@Slf4j
@Service("FishServer")
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
        try {
            //1 reactor 线程
            b.group(bg,wg);
            //2 nio类型的channel
            b.channel(NioServerSocketChannel.class);
            // 监听端口
            b.localAddress(new InetSocketAddress(port));

            //4 通道选项
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.ALLOCATOR,
                    PooledByteBufAllocator.DEFAULT);
        } finally {

        }


    }
}
