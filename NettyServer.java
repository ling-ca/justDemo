package com.ling.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
//nettys erver
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        /**
         * 主线程池，处理客户端的链接
         */
        NioEventLoopGroup main = new NioEventLoopGroup();
        /**
         * 从线程池，处理客户端的读写
         */
        NioEventLoopGroup sub = new NioEventLoopGroup();

        //创建netty引导类，配置，串联一系列的netty组件
        ServerBootstrap bootstrap = new ServerBootstrap();

        //1.设置netty的线程模型
        bootstrap.group(main, sub);
        //2.设置netty的通道类型
        bootstrap.channel(NioServerSocketChannel.class);
        //3.设置客户端处理器(链式)
        bootstrap.childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();

                pipeline.addLast(new HttpServerCodec()); // HttpRequset
                pipeline.addLast(new HttpObjectAggregator(1024*10)); // FullHttpReqeust

                // 添加WebSocket解编码
                pipeline.addLast(new WebSocketServerProtocolHandler("/"));

                // 添加处理客户端的请求的处理器
                pipeline.addLast(new ServerHandlerWebsocket());//自定义的处理器，in：string  out：string
            }
        });

        //4.异步绑定端口号
        ChannelFuture channelFuture = bootstrap.bind(8081);//拿到绑定成功的消息
        channelFuture.sync();//阻塞住，直到服务端成功绑定端口号

        System.out.println("绑定成功！服务端成功启动！");


    }
}
