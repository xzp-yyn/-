package org.xzp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzp.dto.SetmealDto;
import org.xzp.entity.Setmeal;
import org.xzp.entity.SetmealDish;
import org.xzp.mapper.SetmealMapper;
import org.xzp.service.SetmealDishService;
import org.xzp.service.SetmealService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/11 18:13
 * @Version 1.0
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public SetmealDto getmealdto(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(wrapper);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Override
    public void UpdateSetAndDish(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(wrapper);

        dishes=dishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(dishes);
    }
}
