package org.xzp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xzp.entity.Employee;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/9 15:56
 * @Version 1.0
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
