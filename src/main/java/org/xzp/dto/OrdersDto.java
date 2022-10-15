package org.xzp.dto;

import lombok.Data;
import org.xzp.entity.OrderDetail;
import org.xzp.entity.Orders;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/14 18:54
 * @Version 1.0
 */
@Data
public class OrdersDto extends Orders {
    private List<OrderDetail> orderDetails=new ArrayList<>();

    private Integer sunNum;


}
