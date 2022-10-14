package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.R;
import org.xzp.entity.Category;
import org.xzp.service.CategoryService;

import java.util.List;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 17:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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
        return R.success("添加成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id){
        categoryService.removeById(id);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("更新成功！");
    }
    //获取菜品分类
    @GetMapping(value = "/list",params = {"type"})
    public R<List> list(int type){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType,type);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);
    }

    @GetMapping(value = "/list")
    public R<List> FrontCategoryList(){
        List<Category> list = categoryService.list();
        return R.success(list);
    }


}
