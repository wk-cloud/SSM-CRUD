package com.ssm.crud.controller;

import com.ssm.crud.bean.Department;
import com.ssm.crud.bean.Message;
import com.ssm.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName DepartmentController
 * @Description 处理和部门相关的请求
 * @Author wk
 * @Date 2022/5/1 10:40
 * @Version 1.0
 */
@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
    * @author wk
    * @Description 返回所有部门信息
    * @Date 10:44 2022/5/1
    * @Param
    * @Return
    */
    @RequestMapping("/department")
    @ResponseBody
    public Message getDepartmentList(){
        List<Department> departmentList = departmentService.getDepartmentList();
        Message department = Message.success().add("department", departmentList);
        return department;
    }

}
