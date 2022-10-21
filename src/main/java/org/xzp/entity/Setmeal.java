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
 * @Date 2022/10/11 18:10
 * @Version 1.0
 */
@Data
@ApiModel("套餐")

public class Setmeal implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    @ApiModelProperty("分类id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;


    @ApiModelProperty("套餐名称")
    private String name;


    @ApiModelProperty("套餐价格")
    private BigDecimal price;


    @ApiModelProperty("套餐状态 0:停用 1:启用")
    private Integer status;


    @ApiModelProperty("编码")
    private String code;


    @ApiModelProperty("描述信息")
    private String description;


    @ApiModelProperty("套餐图片")
    private String image;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;



    @ApiModelProperty("是否删除")
    private Integer isDeleted;
}
