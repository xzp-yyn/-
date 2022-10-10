package org.xzp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xzp.common.R;
import org.xzp.entity.Employee;
import org.xzp.service.EmployeeService;

import java.util.Base64;
import java.util.List;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/9 15:51
 * @Version 1.0
 */
@SpringBootTest
@Slf4j
public class EmployeeTest {

    @Autowired
    private EmployeeService service;

    @Test
    void test(){
//        List<Employee> list = service.list();
//        System.out.println(list.get(0).getPassword());
        IPage page2 = service.page(new Page<>(1,10));
        System.out.println(page2.getRecords());
    }
}
