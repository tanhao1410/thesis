package com.github.tanhao1410.thesis.server.comm;

import com.github.tanhao1410.thesis.protocol.bean.ClientInfo;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * 管理客户端的连接
 *
 * @author tanhao
 * @date 2021/02/25 12:52
 */
public class ClientChannelManagment {

    private static HashMap<String, ChannelHandlerContext> clientChannelMap = new HashMap();
    private static HashMap<String, ClientInfo> clientInfoMap = new HashMap();

    /**
     * 加入一个客户端连接
     *
     * @param ctx
     * @param clientInfo
     */
    public static void clientChannelJoin(ChannelHandlerContext ctx, ClientInfo clientInfo) {
        //得到client的ip 和端口号
        final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = socketAddress.getAddress().getHostAddress();
        int port = socketAddress.getPort();
        clientChannelMap.put(ip + ":" + port, ctx);

        clientInfoMap.put(ip + ":" + port,clientInfo);
    }

    /**
     * 根据名称获取到一个连接 名称：ip+":"+port
     *
     * @param name
     * @return
     */
    public static ChannelHandlerContext getClientChannelByName(String name) {
        return clientChannelMap.get(name);
    }

    /**
     * 根据名称获取到一个连接 名称：ip+":"+port
     *
     * @param name
     * @return
     */
    public static ClientInfo getClientInfoByName(String name) {
        return clientInfoMap.get(name);
    }

    /**
     * 移除掉一个客户端连接
     *
     * @param ctx
     */
    public static void removeClient(ChannelHandlerContext ctx) {
        //得到client的ip 和端口号
        final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = socketAddress.getAddress().getHostAddress();
        int port = socketAddress.getPort();

        if (clientChannelMap.containsKey(ip + ":" + port)) {
            clientChannelMap.remove(ip + ":" + port);
        }
        if(clientInfoMap.containsKey(ip + ":" + port)){
            clientInfoMap.remove(ip + ":" + port);
        }
    }
}
