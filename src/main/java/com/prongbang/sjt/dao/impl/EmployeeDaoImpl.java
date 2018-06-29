package com.prongbang.sjt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.stereotype.Repository;

import com.prongbang.sjt.dao.AbstractDao;
import com.prongbang.sjt.dao.EmployeeDao;
import com.prongbang.sjt.ddl.DDL;
import com.prongbang.sjt.ddl.Table;
import com.prongbang.sjt.entities.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao, AbstractDao<Employee, Integer> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	public List<Employee> findAll() {

		String sql = "SELECT * FROM EMPLOYEE";

		List<Employee> employees = new ArrayList<Employee>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			Employee employee = new Employee();
			employee.setId(Integer.parseInt(String.valueOf(row.get("ID"))));
			employee.setName((String) row.get("NAME"));
			employee.setAge(Integer.parseInt(String.valueOf(row.get("AGE"))));
			employees.add(employee);
		}

		return employees;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Employee findByPK(Integer pk) throws Exception {
		
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";

		Employee employee = (Employee) jdbcTemplate.queryForObject(sql, new Object[] { pk }, new BeanPropertyRowMapper(Employee.class));

		return employee;
	}

	public int save(Employee entity) throws Exception {
		
		String sql = "INSERT INTO EMPLOYEE (ID, NAME, AGE) VALUES (?, ?, ?)";

		return jdbcTemplate.update( sql, new Object[] { entity.getId(), entity.getName(), entity.getAge() });
	}

	public int update(Employee entity) throws Exception {
		
		return jdbcTemplate.update(DDL.updateSQL(Table.EMPLOYEE, entity, new String[]{"id"}), 
                entity.getName(), 
                entity.getAge(), 
                entity.getId()
        );
	}

	public int delete(Employee entity) throws Exception { 
		
		String sqlDelete = "DELETE FROM EMPLOYEE WHERE ID = ?";
		
		return jdbcTemplate.update(sqlDelete, entity.getId());
	}

	public Employee findLastPk() {
		String sql = "SELECT TOP 1 * FROM EMPLOYEE ORDER BY ID DESC";

		Employee employee = new Employee();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) { 
			employee.setId(Integer.parseInt(String.valueOf(row.get("ID"))));
			employee.setName((String) row.get("NAME"));
			employee.setAge(Integer.parseInt(String.valueOf(row.get("AGE"))));
		}
		return employee;
	} 

}
