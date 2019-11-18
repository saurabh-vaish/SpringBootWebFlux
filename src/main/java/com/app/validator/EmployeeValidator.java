package com.app.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.app.dto.EmployeeDto;

@Component
public class EmployeeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return EmployeeDto.class.equals(clazz);
	}


	@Override
	public void validate(Object target, Errors errors) {

		EmployeeDto emp = (EmployeeDto) target;

		String regname = "^[A-Za-z\\s]+$";
		
		String regusername = "^[A-Za-z0-9\\s]+$";

		String regemail = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
		
		String regmobile = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$";

		// for name

		if(StringUtils.isEmpty(emp.getEmpName()))
		{
			errors.rejectValue("empName",null, "Name Can't Be Empty !");
		}
		else if(!Pattern.matches(regname, emp.getEmpName()))
		{
			errors.rejectValue("empName",null, "Please Enter Valid Name !");
		}


		// for username

		if(StringUtils.isEmpty(emp.getEmpUserName()))
		{
			errors.rejectValue("empUserName",null, "UserName Can't Be Empty !");
		}
		else if(!Pattern.matches(regusername, emp.getEmpUserName()))
		{
			errors.rejectValue("empUserName",null, "Please Enter Valid UserName !");
		}

		// for email

		if(StringUtils.isEmpty(emp.getEmpEmail()))
		{
			errors.rejectValue("empEmail",null, "Email Can't Be Empty !");
		}
		else if(!Pattern.matches(regemail, emp.getEmpEmail()))
		{
			errors.rejectValue("empEmail",null, "Please Enter Valid Email !");
		}

		// for mobile


		if(StringUtils.isEmpty(emp.getEmpMobile()))
		{
			errors.rejectValue("empMobile",null, "Mobile No. Can't Be Empty !");
		}
		else if(!Pattern.matches(regmobile, emp.getEmpMobile().toString()))
		{
			errors.rejectValue("empMobile",null, "Please Enter Valid Mobile Number !");
		}


		

	}

}
