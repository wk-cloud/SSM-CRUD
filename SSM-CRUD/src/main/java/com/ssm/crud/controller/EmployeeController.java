package com.ssm.crud.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.crud.bean.Employee;
import com.ssm.crud.bean.Message;
import com.ssm.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName EmployeeController
 * @Description 处理员工CRUD请求
 * @Author wk
 * @Date 2022/4/29 15:41
 * @Version 1.0
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    /**
    * @author wk
    * @Description 删除指定员工(单个删除或批量删除)
     *             批量删除 1-2-3
     *             单个删除 1
    * @Date 22:44 2022/5/1
    * @Param
    * @Return
    */
    @RequestMapping(value = "/employee/{employeeIds}",method = RequestMethod.DELETE)
    @ResponseBody
    public Message deleteEmployeeById(@PathVariable("employeeIds") String employeeIds){
        ArrayList<Integer> employeeIdList = new ArrayList<>();
        if(employeeIds.contains(",")){
            String[] employeeIdStrArray = employeeIds.split(",");
            for(String employeeId : employeeIdStrArray){
                employeeIdList.add(Integer.parseInt(employeeId));
            }
            Integer integer = employeeService.deleteEmployeeBatch(employeeIdList);
            if(integer > 0){
                return Message.success();
            }
        }else {
            Integer employeeId = Integer.parseInt(employeeIds);
            Integer integer = employeeService.deleteEmployeeById(employeeId);
            if(integer > 0){
                return Message.success();
            }
        }
        return Message.fail();
    }



    /**
    * @author wk
    * @Description 修改员工
     *            如果直接发送 ajax=PUT 形式的请求
     *            封装的数据
     *            Employee：[employeeId=1,employeeName=null,email=null,gender=null,departmentId=null,department = null]
     *
     *            问题：请求体中有数据，但是Employee对象封装不上
     *
     *            原因：1、tomcat将请求体中的数据，封装为一个map
     *                 2、request.getParameter("employeeName"),就会从这个map中取值
     *                 3、SpringMVC封装POJO对象的时候，会把POJO中每个属性的值，request.getParameter("email");
     *
     *            ajax发送PUT请求引发的血案
     *                  PUT请求：请求体中的数据，request.getParameter("employeeName")拿不到
     *                  原因：Tomcat一看是PUT请求，所以不会封装请求体中的数据为map，只有POST请求形式的请求才封装请求体为map
     *
     *      解决方案：
     *     我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
     *     配置上 HttpPutFormContentFilter 过滤器
     *     他的作用：将请求体中的数据解析包装成一个map，
     *              request被重新包装，request.getParameter()被重写，就会从自己封装的map取出数据
     *
    * @Date 21:24 2022/5/1
    * @Param
    * @Return
    */
    @RequestMapping(value = "/employee/{employeeId}",method = RequestMethod.PUT)
    @ResponseBody
    public Message updateEmployee(Employee employee){
        employeeService.updateEmployee(employee);
        return Message.success();
    }


    /**
    * @author wk
    * @Description 根据id，查询员工
    * @Date 21:24 2022/5/1
    * @Param
    * @Return
    */

    @RequestMapping(value = "/employee/{employeeId}",method = RequestMethod.GET)
    @ResponseBody
    public Message getEmployeeById(@PathVariable("employeeId") Integer employeeId){
        Employee employeeById = employeeService.getEmployeeById(employeeId);
        return Message.success().add("employeeById",employeeById);
    }


    /**
    * @author wk
    * @Description 检查员工姓名是否重复
    * @Date 21:50 2022/4/30
    * @Param
    * @Return
    */
    @RequestMapping("/checkEmployeeName")
    @ResponseBody
    public Message checkEmployeeName(String employeeName){
        // 首先校验名字格式是否合法
        String regName = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u4e00-\\u9fa5]{2,5})";
        if(!employeeName.matches(regName)){
            return Message.fail().add("va_message","用户名必须是6-16位数字和字母的组合或2-5位中文");
        }
        // 再校验名字是否重复
        Boolean aBoolean = employeeService.checkEmployeeName(employeeName);
        if(aBoolean){
            return Message.success();
        }else {
            return Message.fail().add("va_message","用户名已经存在，请更换一个试试");
        }
    }



    /**
    * @author wk
    * @Description 保存员工信息
     *             1、支持JSR303校验
     *             2、导入 Hibernate-Validator 依赖
    * @Date 16:58 2022/4/30
    * @Param
    * @Return
    */
    @RequestMapping(value = "/employee",method = RequestMethod.POST)
    @ResponseBody
    public Message saveEmployee(@Valid Employee employee, BindingResult result){
        // 后端校验（用户名和邮箱是否合法）
        if(result.hasErrors()){
            // 校验失败，返回失败信息，在模态框中显示检验失败信息
            HashMap<String, Object> map = new HashMap<>();
            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError fieldError : errorList){
                System.out.println("错误字段名：" + fieldError.getField());
                System.out.println("错误信息：" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Message.fail().add("failMap",map);
        }else {
            employeeService.saveEmployee(employee);
            return Message.success();
        }
    }



    /**
    * @author wk
    * @Description 返回json格式的员工信息
    * @Date 22:23 2022/4/29
    * @Param
    * @Return
    */

    @RequestMapping("/employeeList")
    @ResponseBody
    public Message getEmployeeListByJson(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber){
        // 开启分页查询,传入页码，以及每页的数量
        PageHelper.startPage(pageNumber,5);
        // 查询员工的信息列表（分页后的数据）
        List<Employee> employeeList = employeeService.getEmployeeList();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就可以了
        // 封装了详细的分页信息，包括有我们查询出来的数据。 设置 分页导航数目为 5
        PageInfo<Employee> employeePageInfo = new PageInfo<>(employeeList,5);
        return Message.success().add("employeePageInfo",employeePageInfo);
    }



    /**
    * @author wk
    * @Description 进入员工列表页面
    * @Date 15:43 2022/4/29
    * @Param
    * @Return
    */
    @RequestMapping("/index1")
    public String intoEmployeeList(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber, Model model){
        // 开启分页查询,传入页码，以及每页的数量
        PageHelper.startPage(pageNumber,10);
        // 查询员工的信息列表（分页后的数据）
        List<Employee> employeeList = employeeService.getEmployeeList();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就可以了
        // 封装了详细的分页信息，包括有我们查询出来的数据。 设置 分页导航数目为 5
        PageInfo<Employee> employeePageInfo = new PageInfo<>(employeeList,5);
        // 将数据放置到 model 中
        model.addAttribute("employeePageInfo",employeePageInfo);
        return "index1";
    }

}
