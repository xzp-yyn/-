package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.FillThreadLocal;
import org.xzp.common.R;
import org.xzp.entity.Address;
import org.xzp.entity.User;
import org.xzp.service.AddressService;

import java.util.List;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 11:02
 * @Version 1.0
 */
@RestController
@RequestMapping("/addressBook")
@Api(tags = "收货地址相关接口")
public class AddressController {

    @Autowired
    private AddressService service;

    @GetMapping("/list")
    @ApiOperation("获取当前用户所有地址")
    public R<List> list(){
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId,FillThreadLocal.getvalue());
        List<Address> list = service.list(wrapper);
        return R.success(list);
    }

    @PostMapping
    @ApiOperation("新增地址")

    public R<String> saveaddress(@RequestBody Address address){
        address.setUserId(FillThreadLocal.getvalue());
        service.save(address);
        return R.success("保存成功！");
    }

    @PutMapping("/default")
    @ApiOperation("设置为默认地址")

    public R<String> isdefault(@RequestBody Address address){
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId,FillThreadLocal.getvalue());
        address.setIsDefault(0);
        service.update(address,wrapper);
        Address address1 = new Address();
        address1.setId(address.getId());
        address1.setIsDefault(1);
        service.updateById(address1);
        return R.success("更新默认地址成功");
    }

    @GetMapping("/default")
    @ApiOperation("获取默认地址")

    public R<Address> cartaddressdefault(){
        Long userId = FillThreadLocal.getvalue();
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId,userId);
        wrapper.eq(Address::getIsDefault,1);
        Address address = service.getOne(wrapper);
        return R.success(address);
    }

    @DeleteMapping
    @ApiOperation("删除地址")

    public R<String> deleteAddress(Long ids){
        service.removeById(ids);
        return R.success("删除地址成功！");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取要更新的地址")
    public R<Address> editaddress(@PathVariable("id")Long id){
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getId,id);
        Address address = service.getOne(wrapper);
        return R.success(address);
    }

    @PutMapping
    @ApiOperation("更新地址")
    public R<String> EditSave(@RequestBody Address address){
        service.updateById(address);
        return R.success("更新成功！");
    }

}
