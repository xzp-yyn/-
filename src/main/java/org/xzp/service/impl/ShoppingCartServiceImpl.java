package org.xzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xzp.entity.ShoppingCart;
import org.xzp.mapper.ShoppingCartMapper;
import org.xzp.service.ShoppingCartService;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 16:04
 * @Version 1.0
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
