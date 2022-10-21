package org.xzp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/14 15:30
 * @Version 1.0
 */
@Data
@ApiModel("订单")

public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    @ApiModelProperty("用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;


    @ApiModelProperty("订单号")
    private String number;


    @ApiModelProperty("订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
    private Integer status;


    @ApiModelProperty("地址id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long addressBookId;



    @ApiModelProperty("下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;



    @ApiModelProperty("结账时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkoutTime;


    @ApiModelProperty("支付方式")
    private Integer payMethod;



    @ApiModelProperty("实收金额")
    private BigDecimal amount;


    @ApiModelProperty("备注")
    private String remark;


    @ApiModelProperty("用户名")
    private String userName;


    @ApiModelProperty("手机号")
    private String phone;


    @ApiModelProperty("地址")
    private String address;


    @ApiModelProperty("收货人")
    private String consignee;

}
