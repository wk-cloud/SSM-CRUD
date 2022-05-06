import com.github.pagehelper.PageInfo;
import com.ssm.crud.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName MVCTest
 * @Description 使用 Spring 测试模块提供的测试请求功能，测试 crud 请求的正确性
 *              需要 servlet3.0及以上 的支持
 * @Author wk
 * @Date 2022/4/29 16:05
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:springMVC.xml"})
public class MVCTest {

    // 传入 SpringMVC 的ioc
    @Autowired
    WebApplicationContext context;
    // 虚拟 mvc 请求，获取到处理结果
    MockMvc mockMvc;

    // 每次初始化时，都会创建 MockMvc
    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    // 模拟发送请求
    @Test
    public void testPage() throws Exception {
        // 模拟请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employeeList").param("pageNumber", "5")).andReturn();

        // 请求成功以后，请求域中会有pageInfo，我们可以取出pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo employeePageInfo = (PageInfo) request.getAttribute("employeePageInfo");
        System.out.println("当前页码：" + employeePageInfo.getPageNum());
        System.out.println("总页码：" + employeePageInfo.getPageSize());
        System.out.println("总记录数：" + employeePageInfo.getTotal());
        System.out.println("在页面需要连续显示的页码：" + Arrays.toString(employeePageInfo.getNavigatepageNums()));

        // 获取员工数据
        List<Employee> list = employeePageInfo.getList();
        System.out.println("员工信息如下：");
        list.forEach(System.out::println);
    }

}
