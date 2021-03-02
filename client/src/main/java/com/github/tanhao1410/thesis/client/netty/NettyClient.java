package com.github.tanhao1410.thesis.client.netty;

import com.github.tanhao1410.thesis.client.handler.ClientHandler;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("nettyClient")
public class NettyClient {

    @Value("${netty.server.ip}")
    private String host; // ip地址

    @Value("${netty.server.port}")
    private int port; // 端口
    @Value("${netty.client.ip}")
    private String localIp;
    @Value("${netty.client.port}")
    private int localPort;
    // 通过nio方式来接收连接和处理连接
    private EventLoopGroup group = new NioEventLoopGroup();

    private Bootstrap bootstrap;


    /**
     * 唯一标记
     */
    private boolean initFalg = true;

    /**
     * Netty创建全部都是实现自AbstractBootstrap。 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
     **/
    public void run() {
        bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        final NettyClient client = this;
        //初始化pipeline
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline ph = socketChannel.pipeline();

                ph.addLast(new IdleStateHandler(0, 0, 10));

                ph.addLast("decoder", new ProtobufDecoder(MessageProtocolInfo.MessageProtocol.getDefaultInstance()));
                ph.addLast("encoder", new ProtobufEncoder());
                //业务逻辑实现类
                ph.addLast("nettyClientHandler", new ClientHandler(client));
            }
        });
        bootstrap.remoteAddress(host, port);

        bootstrap.localAddress(localIp, localPort);
        doConnect();
    }

    /**
     * 重连
     */
    public void doConnect() {
        ChannelFuture f = null;
        try {
            if (bootstrap != null) {
                f = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                    final EventLoop eventLoop = futureListener.channel().eventLoop();
                    if (!futureListener.isSuccess()) {
                        System.out.println("与服务端断开连接!在10s之后准备尝试重连!");
                        eventLoop.schedule(() -> doConnect(), 10, TimeUnit.SECONDS);
                    }
                });
                if (initFalg) {
                    System.out.println("Netty客户端启动成功!");
                    initFalg = false;
                }
                // 阻塞
                f.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            System.out.println("客户端连接失败!" + e.getMessage());
        }

    }
}