package com.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.EmployeeDto;
import com.app.model.Employee;
import com.app.repo.EmployeeRepository;
import com.app.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	@Override
	public Mono<EmployeeDto> saveEmployee(Mono<EmployeeDto> e) {

		EmployeeDto emp = e.block();
		
		Employee employee = new Employee();
		employee.setId(emp.getId());
		employee.setEmpName(emp.getEmpName());
		employee.setEmpUserName(emp.getEmpUserName());
		employee.setEmpEmail(emp.getEmpEmail());
		employee.setEmpMobile(emp.getEmpMobile());
		employee.setEmpAddress(emp.getEmpAddress());
		
		repo.save(employee).doOnError((exp) -> new RuntimeException("Error occured"));
		
		return Mono.just(emp);
	}

	@Override
	public Mono<EmployeeDto> updateEmployee(EmployeeDto emp) {
		
		Employee employee = new Employee();
		employee.setId(emp.getId());
		employee.setEmpName(emp.getEmpName());
		employee.setEmpUserName(emp.getEmpUserName());
		employee.setEmpEmail(emp.getEmpEmail());
		employee.setEmpMobile(emp.getEmpMobile());
		employee.setEmpAddress(emp.getEmpAddress());
		
		repo.save(employee).doOnError((e) ->  new RuntimeException("Error occured"));
		
		return Mono.just(emp);
	}

	
	@Override
	@Transactional(readOnly = true)
	public Mono<EmployeeDto> getOneEmployee(String empId) {
		
		Employee emp = repo.findById(empId).defaultIfEmpty(new Employee()).block();
		
		EmployeeDto employee = new EmployeeDto();
		employee.setId(emp.getId());
		employee.setEmpName(emp.getEmpName());
		employee.setEmpUserName(emp.getEmpUserName());
		employee.setEmpEmail(emp.getEmpEmail());
		employee.setEmpMobile(emp.getEmpMobile());
		employee.setEmpAddress(emp.getEmpAddress());
		
		return Mono.just(employee).doOnError((e) ->  new RuntimeException("Error occured"));
		
	}

	
	@Override
	@Transactional(readOnly = true)
	public Flux<EmployeeDto> getAllEmployees() {
		return repo.findAll(Sort.by(Direction.ASC, "empId")).map(emp -> {
			
			EmployeeDto employee = new EmployeeDto();
			employee.setId(emp.getId());
			employee.setEmpName(emp.getEmpName());
			employee.setEmpUserName(emp.getEmpUserName());
			employee.setEmpEmail(emp.getEmpEmail());
			employee.setEmpMobile(emp.getEmpMobile());
			employee.setEmpAddress(emp.getEmpAddress());
			
			return employee;
		});
	}

	@Override
	public Mono<Void> deleteEmployee(String empId) {
		return repo.existsById(empId).block() ? repo.deleteById(empId) : Mono.empty();
	}

	@Override
	public Mono<Boolean> checkEmployee(String empId) {
		return repo.existsById(empId);
	}

	@Override
	@Transactional(readOnly = true)
	public Flux<EmployeeDto> getEmployeeByUserName(Mono<String> username) {
		
		Flux<EmployeeDto> flux = repo.findByEmpUserName(username).defaultIfEmpty(new Employee()).map(emp -> {
		
		EmployeeDto employee = new EmployeeDto();
		employee.setId(emp.getId());
		employee.setEmpName(emp.getEmpName());
		employee.setEmpUserName(emp.getEmpUserName());
		employee.setEmpEmail(emp.getEmpEmail());
		employee.setEmpMobile(emp.getEmpMobile());
		employee.setEmpAddress(emp.getEmpAddress());
		
		return employee;
		});
		
		return flux.doOnError((e) ->  new RuntimeException("Error occured"));
	}
	
	
	
}
