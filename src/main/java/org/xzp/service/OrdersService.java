package org.xzp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.xzp.dto.OrdersDto;
import org.xzp.entity.Orders;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/14 15:39
 * @Version 1.0
 */
public interface OrdersService extends IService<Orders> {

    //下单操作
    public void submit(Orders orders);

}
