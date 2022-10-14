package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.FillThreadLocal;
import org.xzp.common.R;
import org.xzp.entity.ShoppingCart;
import org.xzp.service.ShoppingCartService;

import java.util.List;
/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 16:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/list")
    public R<List> cart(){
        List<ShoppingCart> list = cartService.list();
        return R.success(list);
    }
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        //没有提交user_id以及number
        Long userId = FillThreadLocal.getvalue();
        shoppingCart.setUserId(userId);
        //判断购物车是否存在商品，有则number加一，没有就新增
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        Long dishId = shoppingCart.getDishId();
        if(dishId!=null){
            //菜品
            wrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            //套餐
            wrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart one = cartService.getOne(wrapper);
        if(one!=null){
            Integer number = one.getNumber();
            one.setNumber(number+1);
            cartService.updateById(one);
        }else {
            shoppingCart.setNumber(1);
            cartService.save(shoppingCart);
            one=shoppingCart;
        }
        return R.success(one);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,FillThreadLocal.getvalue());
        cartService.remove(wrapper);
        return R.success("已清空购物车啦！");
    }

    @PostMapping("/sub")
    public R<String> releaseone(@RequestBody ShoppingCart shoppingCart){
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,FillThreadLocal.getvalue());
        if(shoppingCart.getDishId()!=null){
            //减dish number
            wrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else {
            wrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart shoppingCart1 = cartService.getOne(wrapper);
        Integer number = shoppingCart1.getNumber();
        if(number==1){
            cartService.removeById(shoppingCart1);
            return R.success("数量减一");
        }
        shoppingCart1.setNumber(number-1);
        cartService.updateById(shoppingCart1);
        return R.success("数量减一");
    }
}
