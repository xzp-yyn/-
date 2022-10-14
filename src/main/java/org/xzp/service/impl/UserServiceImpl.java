package org.xzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xzp.entity.User;
import org.xzp.mapper.UserMapper;
import org.xzp.service.UserService;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 9:54
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
