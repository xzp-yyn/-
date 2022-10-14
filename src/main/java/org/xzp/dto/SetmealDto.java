package org.xzp.dto;

import lombok.Data;
import org.xzp.entity.Setmeal;
import org.xzp.entity.SetmealDish;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/12 9:09
 * @Version 1.0
 */
@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes=new ArrayList<>();
    private String categoryName;
}
