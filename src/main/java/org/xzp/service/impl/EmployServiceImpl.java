package org.xzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xzp.entity.Employee;
import org.xzp.mapper.EmployeeMapper;
import org.xzp.service.EmployeeService;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/9 15:57
 * @Version 1.0
 */
@Service
public class EmployServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
