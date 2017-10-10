package com.example.demo.websocket;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;

@Slf4j
public class EchoWebSocketHandler implements WebSocketHandler {
	@Override
	public Mono<Void> handle(WebSocketSession session) {
		log.info("sessionId:{}", session.getId());
		//return session.send(session.receive().delayElements(Duration.ofSeconds(1)).log());
		return session.send(session.receive().log());
	}
}
