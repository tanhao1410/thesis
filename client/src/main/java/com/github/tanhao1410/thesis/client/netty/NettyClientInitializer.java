package com.github.tanhao1410.thesis.client.netty;

import com.github.tanhao1410.thesis.client.handler.ClientHandler;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.springframework.stereotype.Component;
/**
 * 
* @Title: NettyClientFilter
* @Description: Netty客户端 过滤器
* @Version:1.0.0  
* @author pancm
* @date 2017年10月8日
 */
@Component
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

	
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();

        ph.addLast("decoder", new ProtobufDecoder(MessageProtocolInfo.MessageProtocol.getDefaultInstance()));
        ph.addLast("encoder", new ProtobufEncoder());
        //业务逻辑实现类
        ph.addLast("nettyClientHandler",new ClientHandler());
      
    }
}