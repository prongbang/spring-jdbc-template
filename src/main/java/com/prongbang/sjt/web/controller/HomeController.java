package com.prongbang.sjt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prongbang.sjt.ddl.DDL;
import com.prongbang.sjt.entities.Employee;

@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping(method = RequestMethod.GET)
	public String init(ModelMap model) { 

		Employee entity = new Employee(1, "prongbang", 23);
		String sql = DDL.insertSQL("EMPLOYEE", entity, "");
		sql = DDL.selectAttrSQL("EMPLOYEE", new String[]{"id","name"}, new String[]{"id"});
		System.out.println(sql);
		
		return "index";
	}

}
