package com.github.tanhao1410.thesis.client;

import com.github.tanhao1410.thesis.client.netty.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ClientApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
		NettyClient nettyServer = context.getBean(NettyClient.class);
		nettyServer.run();
	}

}
