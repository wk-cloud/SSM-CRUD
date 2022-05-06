package com.ssm.crud.service;

import com.ssm.crud.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeService {


    /**
    * @author wk
    * @Description 检查用户名是否重复
    * @Date 21:45 2022/4/30
    * @Param
    * @Return
    */
    Boolean checkEmployeeName(String employeeName);


    /**
    * @author wk
    * @Description 更新员工
    * @Date 21:26 2022/5/1
    * @Param
    * @Return
    */
    Integer updateEmployee(Employee employee);


    /**
    * @author wk
    * @Description 根据id，删除员工
    * @Date 22:45 2022/5/1
    * @Param
    * @Return
    */
    Integer deleteEmployeeById(Integer employeeId);

    /**
    * @author wk
    * @Description 根据id，批量删除
    * @Date 17:01 2022/5/2
    * @Param
    * @Return
    */

    Integer deleteEmployeeBatch(List<Integer> employeeIds);


    /**
    * @author wk
    * @Description 根据id，查询员工信息
    * @Date 18:31 2022/5/1
    * @Param
    * @Return
    */
    Employee getEmployeeById(Integer employeeId);

    /**
    * @author wk
    * @Description 保存员工信息
    * @Date 17:00 2022/4/30
    * @Param
    * @Return
    */
    void saveEmployee(Employee employee);


    /**
    * @author wk
    * @Description 查询所有员工列表
    * @Date 15:53 2022/4/29
    * @Param
    * @Return
    */
    List<Employee> getEmployeeList();

}
