package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.R;
import org.xzp.entity.Category;
import org.xzp.service.CategoryService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 17:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private static String key="category:1";
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate<String,Object> template;

    @GetMapping("/page")
    public R<Page> categorys(int page,@RequestParam("pageSize")Integer pagesize){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Category::getSort);
        Page<Category> page1 = categoryService.page(new Page<>(page, pagesize),wrapper);
        return R.success(page1);
    }

    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        template.delete(key);
        return R.success("添加成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id){
        categoryService.removeById(id);
        template.delete(key);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        template.delete(key);
        return R.success("更新成功！");
    }
    //获取菜品分类
    @GetMapping(value = "/list",params = {"type"})
    public R<List> list(int type){
        List<Category> list=null;
        list = (List<Category>) template.opsForValue().get(key);
        if(list!=null){
            return R.success(list);
        }
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType,type);
        list = categoryService.list(wrapper);
        template.opsForValue().set(key,list,120, TimeUnit.MINUTES);
        return R.success(list);
    }

    @GetMapping(value = "/list")
    public R<List> FrontCategoryList(){
        List<Category> list=null;
        list = (List<Category>) template.opsForValue().get(key);
        if(list!=null){
            return R.success(list);
        }
        list = categoryService.list();
        template.opsForValue().set(key,list,120,TimeUnit.MINUTES);
        return R.success(list);
    }


}
