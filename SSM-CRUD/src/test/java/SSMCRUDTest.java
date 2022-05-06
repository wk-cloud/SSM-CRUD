import com.ssm.crud.bean.Department;
import com.ssm.crud.bean.Employee;
import com.ssm.crud.dao.DepartmentMapper;
import com.ssm.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @ClassName SSMCRUDTest
 * @Description  推荐使用Spring的单元测试，可以自动注入我们需要的组件
 *              1. 导入 SpringTest模块
 *              2. 使用 @ContextConfiguration指定Spring 配置文件的位置
 *              3. 直接 autowired 要使用的组件
 * @Author wk
 * @Date 2022/4/28 22:07
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SSMCRUDTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    /**
    * @author wk
    * @Description 测试 DepartmentMapper
    * @Date 22:08 2022/4/28
    * @Param
    * @Return
    */

    @Test
    public void testSelectDepart(){
        // 1. 测试插入几个部门
        //int insert1 = departmentMapper.insertSelective(new Department(null, "开发部"));
        //int insert2 = departmentMapper.insertSelective(new Department(null, "测试部"));
        //System.out.println(insert1 + insert2);

        // 2. 测试插入员工信息
        //int insertEmployee1 = employeeMapper.insertSelective(new Employee(null, "小兰", "女", "123@qq.com", 1));
        //System.out.println(insertEmployee1);

        // 3. 批量插入多个员工：批量，使用 可以执行sqlSession
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for(int i = 0;i < 1000;i++){
            String username = UUID.randomUUID().toString().substring(0, 5) + "-" + i;
            mapper.insertSelective(new Employee(null,username,"女","456@qq.com",1));
        }
        System.out.println("批量完成");

        // 4. 测试删除员工
        //EmployeeMapper mapper1 = sqlSession.getMapper(EmployeeMapper.class);
        //int i = mapper1.deleteByPrimaryKey(5001);
        //System.out.println(i);
    }

}
