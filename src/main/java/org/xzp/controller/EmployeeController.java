package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.xzp.common.R;
import org.xzp.entity.Employee;
import org.xzp.service.EmployeeService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/9 16:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/employee")
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/login")
    @ApiOperation("员工登录")
    public R<Employee> login(@RequestBody Employee employee, HttpSession session){
        String password = employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());

        /**
         * 获取查询对象
         */
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee one = service.getOne(wrapper);
        if(one==null){
            return R.error("没有此员工！");
        }
        //比对密码
        if(!one.getPassword().equals(password)){
            return R.error("密码错误，登录失败！");
        }
        //员工是否已禁用
        if(one.getStatus()==0){
            return R.error("员工已禁用！");
        }
        session.setAttribute("employee",one.getId());
        return R.success(one);
    }

    @PostMapping("/logout")
    @ApiOperation("员工退出登录")

    public R<String> logout(HttpSession session){
        session.removeAttribute("employee");
        return R.success("退出登录");
    }

    @PostMapping
    @ApiOperation("员工添加")
    public R<String> addmember(@RequestBody Employee employee,HttpSession session){

        //默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        service.save(employee);
        return R.success("员工添加成功！");
    }

    /**
     * 查询所有员工 employee/page
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("查询所有员工")
    public R<IPage> employeePage(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                 @RequestParam("pageSize")Integer size,String name){
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        Page<Employee> page1 = service.page(new Page<>(page, size), wrapper);
        return R.success(page1);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取员工")
    public R<Employee> update(@PathVariable("id")Long id){
        Employee employee = service.getById(id);
        return R.success(employee);
    }

    @PutMapping
    @ApiOperation("修改员工状态")
    public R<String> changeStatus(@RequestBody Employee employee,HttpSession session){
        service.updateById(employee);
        return R.success("修改状态成功！");
    }
}
