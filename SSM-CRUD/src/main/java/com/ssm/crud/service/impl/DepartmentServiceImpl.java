package com.ssm.crud.service.impl;

import com.ssm.crud.bean.Department;
import com.ssm.crud.dao.DepartmentMapper;
import com.ssm.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DepartmentServiceImpl
 * @Description TODO
 * @Author wk
 * @Date 2022/5/1 10:42
 * @Version 1.0
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getDepartmentList() {
        List<Department> departments = departmentMapper.selectByExample(null);
        return departments;
    }
}
