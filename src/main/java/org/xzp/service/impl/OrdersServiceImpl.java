package org.xzp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xzp.common.FillThreadLocal;
import org.xzp.dto.OrdersDto;
import org.xzp.entity.*;
import org.xzp.mapper.OrdersMapper;
import org.xzp.service.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/14 15:40
 * @Version 1.0
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Override
    @Transactional
    public void submit(Orders orders) {
        //得到用户
        Long userId = FillThreadLocal.getvalue();
        User user = userService.getById(userId);
        //得到地址
        Address address = addressService.getById(orders.getAddressBookId());
        if(address==null){
            throw new RuntimeException();
        }
        long orderId = IdWorker.getId();
        AtomicInteger amount=new AtomicInteger(0);
        //得到购物车
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> cartList = shoppingCartService.list(wrapper);
        List<OrderDetail> details=cartList.stream().map((item)->{
            OrderDetail detail = new OrderDetail();
            detail.setDishFlavor(item.getDishFlavor());
            detail.setDishId(item.getDishId());
            detail.setImage(item.getImage());
            detail.setName(item.getName());
            detail.setNumber(item.getNumber());
            detail.setOrderId(orderId);
            detail.setSetmealId(item.getSetmealId());
            detail.setAmount(item.getAmount());
            //得到每个菜品总价并累加
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return detail;
        }).collect(Collectors.toList());


        orders.setUserName(user.getName());
        orders.setOrderTime(new Date());
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setStatus(2);
        orders.setCheckoutTime(new Date());
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setPhone(user.getPhone());
        orders.setAddress((address.getProvinceName()==null?"":address.getProvinceName())+
                (address.getCityName()==null?"":address.getCityName())+
                (address.getDistrictName()==null?"":address.getDistrictName())+
                (address.getDetail()));
        orders.setConsignee(address.getConsignee());
        //向订单表插入一条数据
        this.save(orders);

        //向订单明细插入多条数据
        orderDetailService.saveBatch(details);
        //清空购物车
        shoppingCartService.remove(wrapper);
    }

}
