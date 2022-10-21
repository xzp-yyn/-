package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.R;
import org.xzp.entity.Category;
import org.xzp.entity.Setmeal;
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
@Api(tags = "分类相关接口")
public class CategoryController {

    private static String key="category:1";
    private static String key2="category:2";
    @Autowired
    private CategoryService categoryService;


    @Autowired
    private RedisTemplate<String,Object> template;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询分类")
    public R<Page> categorys(int page,@RequestParam("pageSize")Integer pagesize){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Category::getSort);
        Page<Category> page1 = categoryService.page(new Page<>(page, pagesize),wrapper);
        return R.success(page1);
    }

    @PostMapping
    @ApiOperation(value = "添加分类")
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        template.delete(key);
        return R.success("添加成功");
    }

    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public R<String> delete(@RequestParam("ids") Long id){
        categoryService.removeById(id);
        template.delete(key);
        return R.success("删除成功");
    }

    @PutMapping
    @ApiOperation(value = "更新分类")
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        template.delete(key);
        return R.success("更新成功！");
    }
    //获取菜品分类
    @ApiOperation(value = "根据id获取菜品分类")
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
    @ApiOperation(value = "所有分类")
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

    //获取套餐分类
    @ApiOperation(value = "分页获取套餐分类")
    @GetMapping(value = "/list",params = {"type","page","pageSize"})
    public R<List> setmeallist(int type,int page,Integer pageSize){
        List<Category> list=null;
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType,type);
        list=categoryService.list(wrapper);
        return R.success(list);
    }

}
