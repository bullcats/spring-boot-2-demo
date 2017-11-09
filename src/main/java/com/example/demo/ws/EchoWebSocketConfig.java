package com.example.demo.ws;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Configuration
public class EchoWebSocketConfig {
	@Bean
	public UnicastProcessor<String> eventPublisher(){
		return UnicastProcessor.create();
	}

	@Bean
	public Flux<String> events(UnicastProcessor<String> eventPublisher) {
		return eventPublisher
			.replay(25) // 각각의 클라이언트 마다 접속하는 시간이 다르기 때문에, 접속 시점에 history 설정만큼 이전 메시지를 수신한다.
			.autoConnect();
	}

	@Bean
	public HandlerMapping webSocketMapping(UnicastProcessor<String> eventPublisher, Flux<String> events) {
		Map<String, Object> map = new HashMap<>();
		map.put("/websocket/echo", new EchoWebSocketHandler(eventPublisher, events));
		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
		simpleUrlHandlerMapping.setUrlMap(map);

		//Without the order things break :-/
		simpleUrlHandlerMapping.setOrder(10);
		return simpleUrlHandlerMapping;
	}

	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter();
	}

}
