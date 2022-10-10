package org.xzp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        List<Employee> list = service.list();
        System.out.println(list.get(0).getPassword());
    }
}
