package com.aihaibara.client;


import com.aihaibara.commons.codec.ProtobufDecoder;
import com.aihaibara.commons.codec.ProtobufEncoder;
import com.aihaibara.handler.ChatMsgHandler;
import com.aihaibara.handler.ExceptionHandler;
import com.aihaibara.handler.LoginResponceHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Data
@Service("NettyClient")
public class NettyClient  {
    // 服务器ip地址
    @Value("${server.ip}")
    private String host;
    // 服务器端口
    @Value("${server.port}")
    private int port;

    private GenericFutureListener<ChannelFuture> connectedListener;

    @Autowired
    private ChatMsgHandler chatMsgHandler;

    @Autowired
    private LoginResponceHandler loginResponceHandler;


    @Autowired
    private ExceptionHandler exceptionHandler;
//
//
//    private Channel channel;
//    private ChatSender sender;
//    private LoginSender l;

    private EventLoopGroup g;
    Bootstrap bootstrap ;

    public NettyClient() {

        /**
         * 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
         * 都是AbstractBootstrap的子类。
         **/

        /**
         * 通过nio方式来接收连接和处理连接
         */

        g = new NioEventLoopGroup();


    }
    /**
     * 重连
     */
    public void doConnect() {
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(g);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.remoteAddress(host,port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast("decoder", new ProtobufDecoder());
                    ch.pipeline().addLast("encoder", new ProtobufEncoder());
                    ch.pipeline().addLast(null);
                    ch.pipeline().addLast(null);
                    ch.pipeline().addLast(null);
                }
            });
            log.info("客户端连接 [鱼书IM]");

            ChannelFuture connect = bootstrap.connect();
            connect.addListener(connectedListener);
        } catch (Exception e) {
            log.info("客户端连接失败!" + e.getMessage());
        }


    }
    public void close() {
        g.shutdownGracefully();
    }
}
