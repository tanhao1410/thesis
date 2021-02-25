package com.github.tanhao1410.thesis.client.comm;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author tanhao
 * @date 2021/02/25 13:21
 */
public class ClientChannelManagement {

    private static ChannelHandlerContext instance;

    private ClientChannelManagement() {
    }

    public static ChannelHandlerContext getCTX() {
        if (instance == null) {
            System.out.println("未初始化客户端连接管理类");
        }
        return instance;
    }

    public static void initCTX(ChannelHandlerContext ctx) {
        if (instance != null) {
            System.out.println("客户端连接管理类已初始化");
        } else {
            instance = ctx;
        }
    }

}
