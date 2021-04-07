package com.aihaibara.server.server;

import com.aihaibara.commons.codec.ProtobufDecoder;
import com.aihaibara.commons.codec.ProtobufEncoder;
import com.aihaibara.server.hander.ChatRedirectHandler;
import com.aihaibara.server.hander.HeartBeatServerHandler;
import com.aihaibara.server.hander.LoginRequestHandler;
import com.aihaibara.server.hander.ServerExceptionHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * 服务端启动类
 */
@Data
@Slf4j
@Service("ChatServer")
public class ChatServer {
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

    @Autowired
    private LoginRequestHandler loginRequestHandler;

    @Autowired
    private ServerExceptionHandler serverExceptionHandler;

    @Autowired
    private ChatRedirectHandler chatRedirectHandler;

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
            //5 装配流水线
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //解码
                    socketChannel.pipeline().addLast(new ProtobufDecoder());
                    //编码
                    socketChannel.pipeline().addLast(new ProtobufEncoder());
                    //心跳
                    socketChannel.pipeline().addLast(new HeartBeatServerHandler());
                    //登录
                    socketChannel.pipeline().addLast(loginRequestHandler);
                    //聊天
                    socketChannel.pipeline().addLast(chatRedirectHandler);
                    //异常
                    socketChannel.pipeline().addLast(serverExceptionHandler);

                }
            });
            ChannelFuture channelFuture = b.bind().sync();
            ChannelFuture closeFuture =
                    channelFuture.channel().closeFuture();
            closeFuture.sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 8 优雅关闭EventLoopGroup，
            // 释放掉所有资源包括创建的线程
            wg.shutdownGracefully();
            bg.shutdownGracefully();

        }

    }
}
