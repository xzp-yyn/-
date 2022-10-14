package org.xzp.dto;

import lombok.Data;
import org.xzp.entity.Dish;
import org.xzp.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/11 15:01
 * @Version 1.0
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors=new ArrayList<>();

    private String categoryName;
}
