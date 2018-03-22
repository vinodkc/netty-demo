package com.vkc.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by vkc on 3/22/18.
 */
public class ClientHandler extends SimpleChannelInboundHandler {
    int sentCount;
    public ClientHandler(int i) {
      sentCount = i;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext){
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("Netty Rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, Object in) {
        System.out.println("Client received: " + sentCount + " " + ((ByteBuf)in).toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        cause.printStackTrace();
        channelHandlerContext.close();
    }

}
