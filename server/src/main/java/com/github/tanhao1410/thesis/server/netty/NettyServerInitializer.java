package com.github.tanhao1410.thesis.server.netty;

import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.server.handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * @author tanhao
 * @date 2021/02/23 11:24
 */
@Component
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();

        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        //ph.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        // 解码和编码，应和客户端一致
        //传输的协议 Protobuf
        //ph.addLast(new MyMessageDecoder());//解码器
        //ph.addLast(new MyMessageEncoder());//编码器
        ph.addLast(new IdleStateHandler(0, 0, 60));

        ph.addLast("decoder", new ProtobufDecoder(MessageProtocolInfo.MessageProtocol.getDefaultInstance()));
        ph.addLast("encoder", new ProtobufEncoder());
        //业务逻辑实现类
        ph.addLast("nettyServerHandler", new ServerHandler());
    }
}
