package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.R;
import org.xzp.dto.DishDto;
import org.xzp.entity.Dish;
import org.xzp.entity.DishFlavor;
import org.xzp.service.CategoryService;
import org.xzp.service.DishFlavorService;
import org.xzp.service.DishService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 18:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/dish")
@Api(tags = "菜品相关接口")
public class DishController {

    @Value("${ruiji.dishimg}")
    private String baseurl;

    @Autowired
    private DishService service;

    @Autowired
    private DishFlavorService flavorService;

    @Autowired
    private  CategoryService categoryService;

    @Autowired
    private RedisTemplate<String,Object> template;

    @GetMapping("/page")
    @ApiOperation("分页获取所有菜品")
    @Cacheable(value = "dish",key = "#methodName",condition = "#result!=null")
    public R<Page> dishs(int page, @RequestParam("pageSize")int pagesize,String name){
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        wrapper.orderByDesc(Dish::getUpdateTime);
        Page<Dish> dish = service.page(new Page<>(page, pagesize), wrapper);
        Page<DishDto> dishdto = new Page<>();
        //拷贝属性
        BeanUtils.copyProperties(dish,dishdto,"records");
        List<Dish> records = dish.getRecords();
        List<DishDto> dtos = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            String catename = categoryService.getById(item.getCategoryId()).getName();
            BeanUtils.copyProperties(item, dishDto);
            dishDto.setCategoryName(catename);
            return dishDto;
        }).collect(Collectors.toList());
        dishdto.setRecords(dtos);
        return R.success(dishdto);
    }

    @PostMapping("/status/{s}")
    @Transactional
    @ApiOperation("改变菜品状态")
    @CacheEvict(value = "dish",allEntries = true)
    public R<String> statuschanged(@PathVariable("s")int s,Long[] ids){
        List<Dish> dishs = new ArrayList<>();
        Dish one=new Dish();
        for (Long id:
             ids) {
            one = service.getById(id);
            one.setStatus(s);
            dishs.add(one);
        }
        service.updateBatchById(dishs);
        return R.success("修改状态成功！");
    }

    @PostMapping
    @ApiOperation("添加菜品")
    @CacheEvict(value = "dish",allEntries = true)
    public R<String> saveandFlavor(@RequestBody DishDto dishDto){
        service.saveAndFlavor(dishDto);
        return R.success("添加菜品成功！");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取更新的菜品")
    public R<DishDto> editdish(@PathVariable("id") Long id){
        DishDto flavor = service.getByIdFlavor(id);
        return R.success(flavor);
    }

    @GetMapping("/list")
    @ApiOperation("获取某一菜品")
    @Cacheable(value = "dish",key = "#dish.categoryId",unless = "#result==null")
    public R<List<DishDto>> dishCategory(Dish dish){
        List<DishDto> dishDtos=null;
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        wrapper.eq(Dish::getStatus,1);
        List<Dish> list = service.list(wrapper);
        dishDtos= list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,id);
            List<DishFlavor> dishFlavors = flavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dishDtos);
    }

    @DeleteMapping
    @ApiOperation("删除菜品")
    @CacheEvict(value = "dish",allEntries = true)
    public R<String> deletedish(Long[] ids){
        List<Long> longs = Arrays.asList(ids);
        for (Long item:
             longs) {
            Dish dish = service.getById(item);
            if (dish != null) {
                String image = dish.getImage();
                String url = baseurl + image;
                File file = new File(url);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        service.removeBatchByIds(longs);
        return R.success("删除成功！");
        }

    @PutMapping
    @ApiOperation("更新菜品")
    @CacheEvict(value = "dish",allEntries = true)
    public R<String> update(@RequestBody DishDto dishDto){
        service.updateAndFlavor(dishDto);
        return R.success("更新成功！");
    }
}
