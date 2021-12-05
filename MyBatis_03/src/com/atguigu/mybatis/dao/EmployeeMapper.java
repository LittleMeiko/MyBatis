package com.atguigu.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	public Employee getEmpById(Integer id);
	
	public Integer addEmp(Employee employee);
	
	public Boolean updateEmp(Employee employee);
	
	public Long deleateEmpById(Integer id);
	
	public Employee getEmpByIdAndLastname(@Param(value = "id")Integer id, @Param("lastName")String lastName);
}
