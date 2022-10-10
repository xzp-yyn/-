package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xzp.common.R;
import org.xzp.entity.Employee;
import org.xzp.service.EmployeeService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/9 16:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/login")
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
    public R<String> logout(HttpSession session){
        session.removeAttribute("employee");
        return R.success("退出登录");
    }

    @PostMapping
    public R<String> addmember(@RequestBody Employee employee,HttpSession session){

        employee.setCreateTime(new Date());
        //默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setUpdateTime(new Date());
        Long user = (Long) session.getAttribute("employee");
        employee.setUpdateUser(user);
        employee.setCreateUser(user);
        service.save(employee);
        return R.success("员工添加成功！");
    }

}
