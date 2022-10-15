package org.xzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xzp.entity.OrderDetail;
import org.xzp.mapper.OrderDetailMapper;
import org.xzp.service.OrderDetailService;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/14 15:41
 * @Version 1.0
 */
@Service
public class OrderDetailImpl  extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
