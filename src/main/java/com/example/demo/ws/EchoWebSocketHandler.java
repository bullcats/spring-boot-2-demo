package com.example.demo.ws;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

public class EchoWebSocketHandler implements WebSocketHandler {
	private UnicastProcessor<String> eventPublisher;
	private Flux<String> outputEvents;
	private ObjectMapper mapper;

	public EchoWebSocketHandler(UnicastProcessor<String> eventPublisher, Flux<String> events) {
		this.eventPublisher = eventPublisher;
		this.mapper = new ObjectMapper();
		this.outputEvents = Flux.from(events).map(this::toJSON);
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		WebSocketMessageSubscriber subscriber = new WebSocketMessageSubscriber(eventPublisher);
		session.receive()
			.map(WebSocketMessage::getPayloadAsText)
			.map(this::toEvent)
			.subscribe(subscriber::onNext, subscriber::onError, subscriber::onComplete);
		return session.send(outputEvents.map(session::textMessage));
	}

	private String toEvent(String json) {
		return json;
//		try {
//			return mapper.readValue(json, String.class);
//		} catch (IOException e) {
//			throw new RuntimeException("Invalid JSON:" + json, e);
//		}
	}

	private String toJSON(String event) {
		return event;
//		try {
//			return mapper.writeValueAsString(event);
//		} catch (JsonProcessingException e) {
//			throw new RuntimeException(e);
//		}
	}

	private static class WebSocketMessageSubscriber {
		private UnicastProcessor<String> eventPublisher;
		// private Optional<Event> lastReceivedEvent = Optional.empty();

		public WebSocketMessageSubscriber(UnicastProcessor<String> eventPublisher) {
			this.eventPublisher = eventPublisher;
		}

		public void onNext(String event) {
			// lastReceivedEvent = Optional.of(event);
			eventPublisher.onNext(event);
		}

		public void onError(Throwable error) {
			error.printStackTrace();
		}

		public void onComplete() {

		}

	}
}