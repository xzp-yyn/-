package org.xzp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xzp.entity.User;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 9:53
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
