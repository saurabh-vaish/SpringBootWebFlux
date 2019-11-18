package com.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.EmployeeDto;
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


	// check username

	@GetMapping("/check/{empUserName}")
	public @ResponseBody ResponseEntity<Mono<Map<String, String>>> checkUserName(@PathVariable Mono<String> empUserName)
	{
		String msg="";

		EmployeeDto emp = service.getEmployeeByUserName(empUserName).blockFirst();

		Map<String,String> map = new HashMap<>();
		
		if(emp.getEmpId() != null)
		{
			msg="Username already exits !!";
			map.put("msg", msg);
			
			return ResponseEntity.ok(Mono.just(map));
		}
		else {
			map.put("msg", msg);
			return ResponseEntity.ok(Mono.just(map));
		}
		
}


@PostMapping("save")
public Mono<ResponseEntity<String>> saveEmployee(@ModelAttribute Mono<EmployeeDto> employee,Errors errors,Model map)
{
	log.info("Entering into save method");

	validator.validate(employee, errors);

	if(!errors.hasErrors())
	{
		Mono<EmployeeDto> mono = service.saveEmployee(employee);
		log.info("Employee Saved Sucessfully !!");

		return Mono.just(new ResponseEntity<>("Employee Saved Sucessfully !!",HttpStatus.OK));
	}
	else
	{
		log.info("Employee Details Are Not Correct ");

		return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}



// show all employees

//@GetMapping("/all")
//public Flux<ResponseEntity<Flux<EmployeeDto>>> getAllEmployees()
//{
//	log.info("getting all employees");
//	
//	Flux<EmployeeDto> flux = service.getAllEmployees();
//	
//	return Flux.just(new ResponseEntity<>(flux,HttpStatus.OK));	
//}

@GetMapping("/all")
public ResponseEntity<Flux<EmployeeDto>> getAllEmployees()
{
	log.info("getting all employees");
	
	Flux<EmployeeDto> flux = service.getAllEmployees();
	
	return new ResponseEntity<>(flux,HttpStatus.OK);	
}

/**
// view one employee

@GetMapping("/viewOne/{empId}")
public String viewOneEmployee(@PathVariable Integer empId,Model map,RedirectAttributes rmap)
{
	if(service.checkEmployee(empId))
	{
		map.addAttribute("emp",service.getOneEmployee(empId));

		log.info("Returning Employee Details !");

		return "ViewEmployee";
	}
	else
	{
		log.error("No Employee Found With Given Id");
		throw new EmployeeNotFoundException("Employee Not Found");
	}
}


// get employee update page

@GetMapping("/update/{empId}")
public String getUpdate(@PathVariable Integer empId,Model map)
{
	if(service.checkEmployee(empId))
	{
		map.addAttribute("employee",service.getOneEmployee(empId));

		log.info("Employee Found , Returing Employee Details on Update Page !");

		return "EmployeeUpdate";
	}
	else
	{
		throw new EmployeeNotFoundException("Employee Not Found");
	}
}


// update employee

@PostMapping("/updateEmployee")
public String updateEmployee(@ModelAttribute Employee employee,Errors errors,Model map,RedirectAttributes rmap)
{
	log.info("Entering into update method");

	validator.validate(employee, errors);

	if(!errors.hasErrors())
	{
		Integer empId = service.updateEmployee(employee);
		rmap.addFlashAttribute("smsg","Employee Updated Successfully with Id :"+empId);

		log.info("Employee Updated Sucessfully !!");

		return "redirect:all";
	}
	else
	{
		log.info("Employee Details Are Not Correct ");

		map.addAttribute("employee",employee);
		map.addAttribute("emsg","Please Enter Valid Details");

		return "EmployeeUpdate";
	}

}


// delete Employee 

@GetMapping("/delete/{empId}")
public String deleteEmployee(@PathVariable Integer empId, RedirectAttributes map)
{
	if(service.checkEmployee(empId))
	{
		service.deleteEmployee(empId);

		map.addFlashAttribute("msg","Employee Deleted SuccessFully with Given Id :"+empId);

		log.info("Employee Deleted Successfully  !!");

		return "redirect:/all";
	}
	else
	{
		throw new EmployeeNotFoundException("Employee Not Found");
	}
}

**/
}
