package com.aihaibara.server.server;


import com.aihaibara.commons.codec.ProtobufDecoder;
import com.aihaibara.server.hander.MyServerHander;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

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
            //5 装配流水线
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //解码
                    socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                    socketChannel.pipeline().addLast(new LengthFieldPrepender(4));
                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new MyServerHander());
                }
            });
            ChannelFuture channelFuture = b.bind().sync();
            ChannelFuture closeFuture =
                    channelFuture.channel().closeFuture();
            closeFuture.sync();
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            bg.shutdownGracefully();
            wg.shutdownGracefully();
        }


    }
}
