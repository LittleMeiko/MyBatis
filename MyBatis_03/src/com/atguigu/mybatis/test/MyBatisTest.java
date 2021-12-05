package com.atguigu.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperAnnotation;


/**
 * 1.接口式编程
 * 
 * 2.SqlSession：代表和数据库的一次会话，用完必须关闭
 * 
 * 3.SqlSession和connection都是非线程安全的，每次使用都应该去获取新的对象
 * 
 * 4.Mapper接口没有实现类，但将Mapper接口与接口映射文件绑定后，MyBatis会为Mapper创建一个代理对象
 * 
 * 5.两个配置文件：
 *    (1)全局配置文件(mybatis-config.xml)
 *    (2)sql映射文件(EmployeeMapper.xml)
 *   
 * 
 * @author Meiko
 *
 */
public class MyBatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		return sqlSessionFactory;
	}
	
	/**
	 * 1.根据mybatis-config.xml配置文件(全局配置)创建一个SqlSessionFactory对象:关于数据源及运行环境的配置信息
	 * 
	 * 2.编写sql映射文件：配置sql及sql的封装规则
	 * 
	 * 3.将sql映射文件映射到全局配置文件中
	 * 
	 * 4.写代码:
	 *   (1)根据全局配置文件得到SqlSessionFactory
	 *   (2)由SqlSessionFactory得到SqlSession实例进行增删改查操作，SqlSession是代表和数据库的一次会话，用完要关闭
	 *   (3)根据sql的唯一标识id来执行sql
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		
		//2.获取openSession实例，能支持已经映射的sql语句
		SqlSession openSession = sqlSessionFactory.openSession();
			
		try {
			Employee employee = (Employee)openSession.selectOne("com.atguigu.mybatis.dao.EmployeeMapper.getEmpById", 1);
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}
	
	@Test
	public void test01() throws IOException {
		// 1.获取SqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		
		// 2.根据SqlSessionFactory获取SqlSession实例
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
			// 3.由SqlSession实例得到EmployeeMapper的实现类，会为接口自动创建一个代理对象,然后由代理对象去执行增删改查操作
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			
			// 4.通过EmployeeMapper的实现类执行对应的sql
			Employee employee = employeeMapper.getEmpById(1);
			System.out.println(employee);
		} finally {
			// 5.关闭SqlSession
			openSession.close();
		}
	}
	
	@Test
	public void test02() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
			EmployeeMapperAnnotation annotationMapper = openSession.getMapper(EmployeeMapperAnnotation.class);
			Employee empById = annotationMapper.getEmpById(1);
			System.out.println(empById);
		} finally {
			openSession.close();
		}
	
	}
	
	/**
	 * 测试增删改查:
	 *  1.MyBatis允许增删改操作直接定义以下类型的返回值：Integer、Long、Boolean,以及以上对应的普通类型
	 *  
	 *  2.需要手动提交数据
	 *
	 * 
	 * @throws IOException
	 */
	@Test
	public void test03() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取不会自动提交执行结果的SqlSession
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			//添加
			Employee employee = new Employee(null, "Jerry", "1", "jerry@atguigu.com");
			Integer addEmp = mapper.addEmp(employee);
			System.out.println("====>自增主键值：" + employee.getId());
			System.out.println(addEmp > 0 ? "添加成功" : "添加失败");
			
			//修改
//			Employee employee = new Employee(4, "Jerry", "0", "jerry@163.com");
//			Boolean isUpdate = mapper.updateEmp(employee);
//			System.out.println(isUpdate ? "修改成功" : "修改失败");
			
			//删除
//			Long rows = mapper.deleateEmpById(4);
//			System.out.println(rows > 0 ? "删除成功" : "删除失败");
			
			//执行完sql自动提交
			openSession.commit();
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * 测试多参数sql的查询
	 * 
	 * @throws IOException
	 */
	@Test
	public void test04() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取不会自动提交执行结果的SqlSession
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpByIdAndLastname(1, "tom");
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}
}
