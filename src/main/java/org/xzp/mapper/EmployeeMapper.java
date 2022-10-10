package org.xzp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.xzp.entity.Employee;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/9 15:56
 * @Version 1.0
 */
@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
}
