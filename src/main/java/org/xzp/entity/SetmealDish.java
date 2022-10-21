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
 * @Date 2022/10/12 16:48
 * @Version 1.0
 */
@Data
@ApiModel("套餐中菜品")
public class SetmealDish implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;



    @ApiModelProperty("套餐id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long setmealId;



    @ApiModelProperty("菜品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dishId;



    @ApiModelProperty("菜品名称")
    private String name;


    @ApiModelProperty("菜品原价")
    private BigDecimal price;


    @ApiModelProperty("份数")
    private Integer copies;



    @ApiModelProperty("排序")
    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;



    @ApiModelProperty("是否删除")
    private Integer isDeleted;
}
