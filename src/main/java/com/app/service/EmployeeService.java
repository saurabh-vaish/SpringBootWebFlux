package com.app.service;

import com.app.dto.EmployeeDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

	public Mono<EmployeeDto> saveEmployee(Mono<EmployeeDto> emp);
	
	public Mono<EmployeeDto> updateEmployee(EmployeeDto emp);
	
	public Mono<EmployeeDto> getOneEmployee(String empId);
	
	public Flux<EmployeeDto> getAllEmployees();
	
	public Mono<Void> deleteEmployee(String empId);
	
	public Mono<Boolean> checkEmployee(String empId);
	
	public Flux<EmployeeDto> getEmployeeByUserName(Mono<String> username);
	 
	
}
