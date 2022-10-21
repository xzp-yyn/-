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
import java.time.LocalDateTime;
import java.util.Date;
/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 16:01
 * @Version 1.0
 */

/**
 * 购物车
 */
@Data
@ApiModel("购物车")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    @ApiModelProperty("菜品名称")
    private String name;


    @ApiModelProperty("用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

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


    @ApiModelProperty("单份金额")
    private BigDecimal amount;


    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}

