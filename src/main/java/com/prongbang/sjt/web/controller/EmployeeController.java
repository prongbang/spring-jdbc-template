package com.prongbang.sjt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prongbang.sjt.entities.Employee;
import com.prongbang.sjt.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService; 

	@RequestMapping(method = RequestMethod.GET)
	public String init(ModelMap model) throws Exception {

		model.addAttribute("employees", employeeService.findAll());

		return "employee";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() throws Exception {

		int pk = (employeeService.findLastPk().getId()) + 1;
		Employee e = new Employee();
		e.setId(pk);
		e.setAge(23);
		e.setName("prongbang");
		employeeService.save(e);

		return "redirect:/employee";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id) throws Exception {

		Employee e = new Employee();
		e.setId(id);
		e.setAge(24);
		e.setName("update");

		employeeService.update(e);

		return "redirect:/employee";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Integer id) throws Exception {

		Employee e = new Employee();
		e.setId(id);

		employeeService.delete(e);

		return "redirect:/employee";
	}

}
