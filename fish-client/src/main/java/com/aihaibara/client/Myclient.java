package com.aihaibara.client;

import com.aihaibara.handler.ClientHander;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


public class Myclient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {


            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group);

            bootstrap.channel(NioSocketChannel.class);

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //解码
                    socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                    socketChannel.pipeline().addLast(new LengthFieldPrepender(4));
                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new ClientHander());

                }
            });
            ChannelFuture future = bootstrap.connect("localhost",15757).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            group.shutdownGracefully();

        }
    }
}
