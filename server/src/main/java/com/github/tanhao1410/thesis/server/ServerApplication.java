package com.github.tanhao1410.thesis.server;

import com.github.tanhao1410.thesis.server.spring.SpringBeanManagement;
import com.github.tanhao1410.thesis.server.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.github.tanhao1410.thesis.common.mapper")
public class ServerApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);
		SpringBeanManagement.initMapper(context);
		NettyServer nettyServer = context.getBean(NettyServer.class);
		nettyServer.run();
	}

}
