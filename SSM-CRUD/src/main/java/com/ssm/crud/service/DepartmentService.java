package com.ssm.crud.service;

import com.ssm.crud.bean.Department;

import java.util.List;

public interface DepartmentService {

    /**
    * @author wk
    * @Description 返回所有部门信息
    * @Date 10:42 2022/5/1
    * @Param
    * @Return
    */
    List<Department> getDepartmentList();
}
