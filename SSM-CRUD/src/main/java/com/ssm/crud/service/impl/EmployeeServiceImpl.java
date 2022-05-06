package com.ssm.crud.service.impl;

import com.ssm.crud.bean.Employee;
import com.ssm.crud.bean.EmployeeExample;
import com.ssm.crud.dao.EmployeeMapper;
import com.ssm.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName EmployeeService
 * @Description TODO
 * @Author wk
 * @Date 2022/4/29 15:44
 * @Version 1.0
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;


    /**
    * @author wk
    * @Description 检查姓名是否重复
    * @Date 21:49 2022/4/30
    * @Param userName
    * @Return  true：代表当前姓名可用，false：代表姓名不可用
    */

    @Override
    public Boolean checkEmployeeName(String employeeName) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeNameEqualTo(employeeName);
        long count = employeeMapper.countByExample(employeeExample);
        return count == 0;
    }

    /**
    * @author wk
    * @Description 更新员工
    * @Date 21:26 2022/5/1
    * @Param
    * @Return
    */

    @Override
    public Integer updateEmployee(Employee employee) {
        int i = employeeMapper.updateByPrimaryKeySelective(employee);
        return i;
    }

    /**
    * @author wk
    * @Description 根据id，删除员工
    * @Date 22:46 2022/5/1
    * @Param
    * @Return
    */

    @Override
    public Integer deleteEmployeeById(Integer employeeId) {
        int i = employeeMapper.deleteByPrimaryKey(employeeId);
        return i;
    }

    /**
    * @author wk
    * @Description 根据id，批量删除
    * @Date 17:00 2022/5/2
    * @Param
    * @Return
    */

    @Override
    public Integer deleteEmployeeBatch(List<Integer> employeeIds) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeIdIn(employeeIds);
        int i = employeeMapper.deleteByExample(employeeExample);
        return i;
    }

    /**
    * @author wk
    * @Description 根据员工id，查询员工信息
    * @Date 18:32 2022/5/1
    * @Param
    * @Return
    */

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        return employee;
    }

    /**
    * @author wk
    * @Description 添加员工
    * @Date 21:42 2022/4/30
    * @Param
    * @Return
    */

    @Override
    public void saveEmployee(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
    * @author wk
    * @Description 返回所有员工列表
    * @Date 15:54 2022/4/29
    * @Param
    * @Return
    */

    @Override
    public List<Employee> getEmployeeList() {
        List<Employee> employees = employeeMapper.selectByExampleWithDepartment(null);
        return employees;
    }
}
