package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.FillThreadLocal;
import org.xzp.common.R;
import org.xzp.dto.OrdersDto;
import org.xzp.entity.OrderDetail;
import org.xzp.entity.Orders;
import org.xzp.service.OrderDetailService;
import org.xzp.service.OrdersService;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/14 17:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService orderService;

    @Autowired
    private OrderDetailService detailService;


    @PostMapping("/submit")
    public R<String> saveOrder(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功！");
    }
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> userpage(int page, Integer pageSize){
        Long userId = FillThreadLocal.getvalue();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId,userId);
        wrapper.orderByDesc(Orders::getCheckoutTime);
        //得到当前用户所有订单
        List<Orders> orders = orderService.list(wrapper);
        List<OrdersDto> ordersDtos= orders.stream().map((item)->{
            OrdersDto ordersDto = new OrdersDto();
            LambdaQueryWrapper<OrderDetail> detailWrapper = new LambdaQueryWrapper<>();
            detailWrapper.eq(OrderDetail::getOrderId,item.getNumber());
            List<OrderDetail> details = detailService.list(detailWrapper);
            ordersDto.setOrderDetails(details);
            ordersDto.setSunNum(details.size());
            BeanUtils.copyProperties(item,ordersDto);
            return ordersDto;
        }).collect(Collectors.toList());
        LambdaQueryWrapper<OrdersDto> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.orderByDesc(OrdersDto::getCheckoutTime);
        Page<Orders> ordersPage = orderService.page(new Page<>(page, pageSize), wrapper);
        Page<OrdersDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(ordersPage,dtoPage,"records");
        dtoPage.setRecords(ordersDtos);
        return R.success(dtoPage);
    }

    @GetMapping("/page")
    public R<Page> ordershow(int page, Integer pageSize, Long number, String beginTime,
                             String endTime){
        Page<Orders> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Orders::getOrderTime);
        wrapper.like(number!=null,Orders::getNumber,number);
        wrapper.apply(beginTime!=null,"UNIX_TIMESTAMP(order_time) >= UNIX_TIMESTAMP('" + beginTime + "')");
        wrapper.apply(endTime!=null,"UNIX_TIMESTAMP(checkout_time) <= UNIX_TIMESTAMP('" + endTime + "')");
        orderService.page(page1,wrapper);
        return R.success(page1);
    }

    @PutMapping
    public R<String> changeStatus(@RequestBody Orders orders){
        orderService.updateById(orders);
        return R.success("状态更新完成！");
    }
}
