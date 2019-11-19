package com.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.EmployeeDto;
import com.app.repo.EmployeeRepository;
import com.app.service.impl.EmployeeServiceImpl;
import com.app.validator.EmployeeValidator;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/")
@Log4j2
public class EmployeeController {

	@Autowired
	private EmployeeServiceImpl service;

	@Autowired
	private EmployeeValidator validator;

	@Autowired
	private EmployeeRepository repo;


	// check username

	@GetMapping("/check/{empUserName}")
	public  ResponseEntity<Flux<Object>> checkUserName(@PathVariable String empUserName)
	{
		Map<String,String> map = new HashMap<>();

		Flux<Object> flux = service.getEmployeeByUserName(empUserName).map(e -> {
			if(e.getId() != null )
			{
				map.put("msg", "Employee Already Exists !!");
			}
			else {
				map.put("msg", "");
			}
			return ResponseEntity.ok(map);
		});

		return ResponseEntity.ok(flux);
	}


	@PostMapping("/save")
	public ResponseEntity<Mono<Object>> saveEmployee(@RequestBody EmployeeDto employee)
	{
		log.info("Entering into save method");


		service.saveEmployee(employee);

		log.info("Employee Saved Sucessfully !!");

		Map<String,String> map = new HashMap<>();
		map.put("msg","Employee Saved Sucessfully !!");

		return ResponseEntity.ok(Mono.just(map));
		//	}
		//	else
		//	{
		//		log.info("Employee Details Are Not Correct ");
		//
		//		return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		//	}

	}



	// show all employees

	//@GetMapping(value="/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@GetMapping(value="/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<EmployeeDto>> getAllEmployees()
	{
		log.info("getting all employees");

		Flux<EmployeeDto> flux = service.getAllEmployees().switchIfEmpty(Flux.just());

		return new ResponseEntity<>(flux,HttpStatus.OK);	
	}



	// view one employee

	@GetMapping("/viewOne/{empId}")
	public ResponseEntity<Mono<Object>> viewOneEmployee(@PathVariable String empId)
	{
		//	if(service.checkEmployee(empId))
		//	{
		log.info("Returning Employee Details !");

		Mono<Object> mono = service.getOneEmployee(empId).map(e -> {
			return ResponseEntity.ok(e);
		});

		return ResponseEntity.ok(mono);
		//		return ResponseEntity.ok(mono);
		//	}
		//	else
		//	{
		//		log.error("No Employee Found With Given Id");
		//		throw new EmployeeNotFoundException("Employee Not Found");
		//	}
	}

	// update employee

	@PostMapping("/updateEmployee")
	public ResponseEntity<Mono<Object>> updateEmployee(@RequestBody EmployeeDto employee)
	{
		log.info("Entering into update method");

		//	validator.validate(employee, errors);
		//
		//	if(!errors.hasErrors())
		//	{
		Map<String,String> map = new HashMap<>();

		Mono<EmployeeDto> m = service.updateEmployee(employee);
		Mono<Object> mono = m.map(e-> {
			map.put("msg","Employee updated Sucessfully with id = "+ e.getId() );
			return ResponseEntity.ok(map);
		} );

		return ResponseEntity.ok(mono);

		//}
		//	else
		//	{
		//		log.info("Employee Details Are Not Correct ");
		//
		//		map.addAttribute("employee",employee);
		//		map.addAttribute("emsg","Please Enter Valid Details");
		//
		//		return "EmployeeUpdate";
		//	}

	}


	// delete Employee 

	@DeleteMapping("/delete/{empId}")
	public ResponseEntity<Mono<Object>> deleteEmployee(@PathVariable String empId)
	{
	
		Map<String,String> map = new HashMap<>();
		Mono<Object> mono = service.deleteEmployee(empId).map(e -> {
			map.put("msg","Employee Deleted SuccessFully with Given Id :"+empId);
			return ResponseEntity.ok(map);
		});

		log.info("Employee Deleted Successfully  !!");

		return ResponseEntity.ok(mono);

	}

}
