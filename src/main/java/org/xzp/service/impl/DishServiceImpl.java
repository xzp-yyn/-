package org.xzp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xzp.dto.DishDto;
import org.xzp.entity.Dish;
import org.xzp.entity.DishFlavor;
import org.xzp.mapper.DishMapper;
import org.xzp.service.CategoryService;
import org.xzp.service.DishFlavorService;
import org.xzp.service.DishService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 18:36
 * @Version 1.0
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>  implements DishService {

    @Autowired
    private DishFlavorService service;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService flavorService;

    @Override
    @Transactional
    public void saveAndFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors=flavors.stream().map((item)->{
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());

        service.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdFlavor(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = this.getById(id);
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = service.list(wrapper);
        dishDto.setFlavors(list);
        dishDto.setCategoryName(categoryService.getById(dishDto.getCategoryId()).getName());
        return dishDto;
    }

    /**
     * 同时更新dish表和口味表，需要先把口味表的旧数据删除
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateAndFlavor(DishDto dishDto) {
        this.updateById(dishDto);

        List<DishFlavor> flavors = dishDto.getFlavors();
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dishDto.getId());
        flavorService.remove(wrapper);

        flavors=flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        flavorService.saveBatch(flavors);
    }
}
