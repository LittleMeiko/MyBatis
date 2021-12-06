package com.atguigu.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	public Employee getEmpById(Integer id);
	
	public Integer addEmp(Employee employee);
	
	public Boolean updateEmp(Employee employee);
	
	public Long deleateEmpById(Integer id);
	
	public Employee getEmpByIdAndLastname(Map<String, Object> map);
	
	public List<Employee> getEmpsLastnameLike(String lastName);
	
	public Map<String, Object> getEmpByIdReturnMap(Integer id);
	
	// 告诉mybatis封装这个map时的key值用Employee的哪个属性
	@MapKey(value = "lastName")
	public Map<String, Employee> getEmpReturnMaps();
}
