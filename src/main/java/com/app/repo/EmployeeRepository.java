package com.app.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.app.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String>
{	
	Flux<Employee> findByEmpUserName(Mono<String> empUserName);
}
