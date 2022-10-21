package org.xzp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/14 15:35
 * @Version 1.0
 */
@Data
@ApiModel("订单详细")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    @ApiModelProperty("名称")
    private String name;


    @ApiModelProperty("订单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;



    @ApiModelProperty("菜品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dishId;



    @ApiModelProperty("套餐id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long setmealId;



    @ApiModelProperty("口味")
    private String dishFlavor;



    @ApiModelProperty("数量")
    private Integer number;


    @ApiModelProperty("金额")
    private BigDecimal amount;


    @ApiModelProperty("图片")
    private String image;
}
