package com.example.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

	@LocalServerPort
	private int port;

	// AsyncRestTemplate 역할을 하는 WebClient (ThreadSafe 함)
	private WebClient webClient;

	@Before
	public void setUp() {
		webClient = WebClient.create("http://localhost:" + port);
	}

	@Test
	public void cars() {
		String body = webClient.get()
			.uri("/cars")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.flatMap(res -> res.bodyToMono(String.class))
			.block();

		log.info("body:{}", body);
	}

	@Test
	public void asyncCars() {
		String body = webClient.get()
			.uri("/asyncCars")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.flatMap(res -> res.bodyToMono(String.class))
			.block();

		log.info("body:{}", body);
	}
}
