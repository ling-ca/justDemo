package com.ling.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ServerHandlerWebsocket extends SimpleChannelInboundHandler<TextWebSocketFrame>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("读取客户端的内容："+textWebSocketFrame.text());
        TextWebSocketFrame textWebSocketFrame1 = new TextWebSocketFrame("某端口号的服务端说：哈哈哈哈");
        channelHandlerContext.channel().writeAndFlush(textWebSocketFrame1);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新客户端连接。。。");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户断开连接。。。");
    }
}
