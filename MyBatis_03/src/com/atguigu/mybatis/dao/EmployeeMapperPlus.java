package com.atguigu.mybatis.dao;

import java.util.List;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapperPlus {
	
	public Employee getEmpById(Integer id);
	
	Employee getEmpAndDept(Integer id);
	
	Employee getEmpByIdStep(Integer id);
	
	List<Employee> getEmpsByDeptId(Integer d_id);

}
