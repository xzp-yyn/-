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
import java.util.Date;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 10:55
 * @Version 1.0
 */
@Data
@ApiModel("收货地址")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    //用户id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;


    //
    @ApiModelProperty("收货人")
    private String consignee;



    @ApiModelProperty("手机号")
    private String phone;



    @ApiModelProperty("0 女 1 男")
    private String sex;


    @ApiModelProperty("省级区划编号")

    private String provinceCode;



    @ApiModelProperty("省级名称")
    private String provinceName;


    @ApiModelProperty("市级区划编号")

    private String cityCode;



    @ApiModelProperty("市级名称")
    private String cityName;



    @ApiModelProperty("区级区划编号")

    private String districtCode;



    @ApiModelProperty("区级名称")
    private String districtName;



    @ApiModelProperty("详细地址")
    private String detail;



    @ApiModelProperty("标签")
    private String label;


    @ApiModelProperty("是否默认 0 否 1是")
    private Integer isDefault;


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

    @ApiModelProperty("是否已删除")
    private Integer isDeleted;
}
