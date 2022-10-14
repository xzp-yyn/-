package org.xzp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xzp.dto.DishDto;
import org.xzp.entity.Dish;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 18:35
 * @Version 1.0
 */
public interface DishService extends IService<Dish> {
    //同时保存菜品和口味
    public void saveAndFlavor(DishDto dishDto);

    //根据Id查询菜品和口味
    public DishDto getByIdFlavor(Long id);

    //更新菜品和口味
    public void updateAndFlavor(DishDto dishDto);
}
