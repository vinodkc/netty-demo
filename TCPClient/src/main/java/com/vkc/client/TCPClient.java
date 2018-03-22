package com.vkc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class TCPClient
{
    public static void main( String[] args )
    {
        System.out.println( "Starting TCPClient ..." );

        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap clientBootstrap = new Bootstrap();

            clientBootstrap.group(group);
            clientBootstrap.channel(NioSocketChannel.class);
            clientBootstrap.remoteAddress(new InetSocketAddress("localhost", 9999));
            for (int i = 0; i < 3 ; i++) {
                final int finalI = i;
                clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ClientHandler(finalI));
                    }
                });
                ChannelFuture channelFuture = clientBootstrap.connect().sync();
                channelFuture.channel().closeFuture().sync();
            }



        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
