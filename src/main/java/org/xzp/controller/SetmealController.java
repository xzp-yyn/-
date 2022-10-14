package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.R;
import org.xzp.dto.DishDto;
import org.xzp.dto.SetmealDto;
import org.xzp.entity.Dish;
import org.xzp.entity.Setmeal;
import org.xzp.entity.SetmealDish;
import org.xzp.service.CategoryService;
import org.xzp.service.SetmealDishService;
import org.xzp.service.SetmealService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/11 18:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Value("${ruiji.dishimg}")
    private String baseurl;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService dishService;


    @GetMapping("/page")
    public R<Page> getallSetmeal(Integer page,Integer pageSize,String name){
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        Page<Setmeal> page1 = setmealService.page(new Page<>(page, pageSize), wrapper);
        Page<SetmealDto> setdto = new Page<>();
        BeanUtils.copyProperties(page1,setdto,"records");
        List<Setmeal> records = page1.getRecords();
        List<SetmealDto> dtos = records.stream().map((item) -> {
            SetmealDto dto = new SetmealDto();
            String category = categoryService.getById(item.getCategoryId()).getName();
            BeanUtils.copyProperties(item, dto);
            dto.setCategoryName(category);
            return dto;
        }).collect(Collectors.toList());
        setdto.setRecords(dtos);
        return R.success(setdto);
    }

    @PostMapping("/status/{s}")
    public R<String> statuschanged(@PathVariable("s")int s, Long[] ids){
        List<Setmeal> setmeals = new ArrayList<>();
        Setmeal one=new Setmeal();
        for (Long id:
                ids) {
            one = setmealService.getById(id);
            one.setStatus(s);
            setmeals.add(one);
        }
        setmealService.updateBatchById(setmeals);
        return R.success("修改状态成功！");
    }

    @PostMapping
    public R<String> saveandFlavor(@RequestBody SetmealDto setmealDto){
        setmealService.save(setmealDto);
        return R.success("添加套餐成功！");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> editdish(@PathVariable("id") Long id){
        SetmealDto setmealDto = setmealService.getmealdto(id);
        return R.success(setmealDto);
    }

    @DeleteMapping
    @Transactional
    public R<String> deletedish(Long[] ids){
        List<Long> longs = Arrays.asList(ids);
        for (Long item:
                longs) {
            String image = setmealService.getById(item).getImage();
            String url=baseurl+image;
            File file = new File(url);
            if(file.exists()){
                file.delete();
            }
        }
        setmealService.removeBatchByIds(longs);
        return R.success("删除成功！");
    }

    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.UpdateSetAndDish(setmealDto);
        return R.success("更新成功！");
    }

    @GetMapping("/list")
    public R<List<SetmealDto>> listsetmealdto(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(Setmeal::getStatus,setmeal.getStatus());
        List<Setmeal> setmeals = setmealService.list(queryWrapper);
        List<SetmealDto> dtoList= setmeals.stream().map((item)->{
            Long itemId = item.getId();
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SetmealDish::getSetmealId,itemId);
            List<SetmealDish> dishes = dishService.list(wrapper);
            setmealDto.setSetmealDishes(dishes);
            return setmealDto;
        }).collect(Collectors.toList());

        return R.success(dtoList);
    }

}
