package com.example.demo.repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Car;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class HelloRepository {

	private static final List<Car> sample = Arrays.asList(
		new Car(1, "car1"),
		new Car(2, "car2"),
		new Car(3, "car3")
	);

	public Mono<List<Car>> selectAll() {
		return Mono.just(sample);
	}

	@Async
	public CompletableFuture<List<Car>> asyncSelectAll() {
		log.info("start asyncSelectAll");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CompletableFuture.completedFuture(sample);
	}

}
