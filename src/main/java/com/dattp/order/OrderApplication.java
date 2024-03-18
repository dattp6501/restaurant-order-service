package com.dattp.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.dattp.order.entity.Cart;
import com.dattp.order.service.CartService;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication implements CommandLineRunner{

	public static void main(String[] args){
		SpringApplication.run(OrderApplication.class, args);
	}

	@Autowired
	private CartService cartService;

	@Override
	public void run(String... args) throws Exception {
		cartService.createCart(new Cart(Long.parseLong("1")));
	}
}