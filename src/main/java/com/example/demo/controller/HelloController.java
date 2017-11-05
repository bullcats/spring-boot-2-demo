package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Car;
import com.example.demo.repository.HelloRepository;

import reactor.core.publisher.Mono;

@RestController
public class HelloController {
	@Autowired
	private HelloRepository helloRepository;

	@GetMapping("/cars")
	public Mono<List<Car>> cars() {
		return helloRepository.selectAll();
	}

	@GetMapping("/asyncCars")
	public Mono<List<Car>> asyncCars() {
		return Mono.fromCompletionStage(helloRepository.asyncSelectAll());
	}
}
