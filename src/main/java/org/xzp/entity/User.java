package org.xzp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 9:48
 * @Version 1.0
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

    private String avatar;

    private Integer status;

}
