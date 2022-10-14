package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xzp.common.R;
import org.xzp.entity.User;
import org.xzp.service.UserService;
import org.xzp.utils.LoginCodeUtils;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 9:54
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/sendCode")
    public R<String> sendCode(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        String code = LoginCodeUtils.generateCode(4);
        log.info("验证码：{}",code);
        session.setAttribute(phone,code);
        return R.success("验证码发送成功");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        String codeInsession = (String) session.getAttribute(phone);

        if(code.equals(codeInsession)){
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone,phone);
            User user2 = service.getOne(wrapper);
            if(user2==null){
                user2=new User();
                user2.setPhone(phone);
                user2.setStatus(1);
                service.save(user2);
            }
            session.setAttribute("user",user2.getId());
            return R.success(user2);
        }
        return R.error("验证失败！");
    }
    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出登录");
    }

}
