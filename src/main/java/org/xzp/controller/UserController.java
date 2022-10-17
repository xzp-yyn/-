package org.xzp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xzp.common.R;
import org.xzp.entity.User;
import org.xzp.service.UserService;
import org.xzp.utils.LoginCodeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    static int PHONE_CODE_SECONDS=60;

    //锁过期时间
    static int LOCK_IP_EXPIRE_SECONDS=12;

    static int PROTECT_CODE_EXPIRE_SECONDS=1;
    @Autowired
    private RedisTemplate<String,Object> template;

    @PostMapping("/sendCode")
    public R<String> sendCode(@RequestBody User user, HttpSession session,HttpServletRequest request){
        Long phone = Long.valueOf(user.getPhone());
        String code = LoginCodeUtils.generateCode(4);
        //生成验证码的·key
        String key="phone:"+phone;
        String msg = this.LockIP(request, phone);
        if("".equals(msg)) {
            if (!template.hasKey(key)) {
                template.opsForValue().set(key, String.valueOf(code));
                template.expire(key, PHONE_CODE_SECONDS,TimeUnit.SECONDS);
                log.info("验证码：{}",code);
                return R.success("验证码发送成功,请注意查看手机短信");
            } else {
                return R.success("验证码已产生，请勿重复获取");
            }
        }else {
            return R.error(msg);
        }
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        String key="phone:"+phone;
        Object value = template.opsForValue().get(key);
        if(code.equals(value)){
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
            template.delete(key);
            return R.success(user2);
        }
        return R.error("验证失败！");
    }
    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出登录");
    }

    /**
     * 锁ip
     *防止恶意访问设置12小时过期时间
     * @param request 请求
     * @return {@link Object}
     */
    public String LockIP(HttpServletRequest request, Long phone){
        String addr = request.getRemoteAddr();
        String LockIPkey="Lockip:"+phone;
        String protectkey="phone:protect";

        if(template.hasKey(LockIPkey)){
            return "12小时内不能获取验证码";
        }
        if(!template.hasKey(protectkey)){
            template.opsForValue().increment(protectkey);
            template.expire(protectkey, PROTECT_CODE_EXPIRE_SECONDS,TimeUnit.DAYS);
            return "";
        }
        template.opsForValue().increment(protectkey);
        if(Integer.parseInt((String) template.opsForValue().get(protectkey))>3){
            template.opsForValue().set(LockIPkey,addr);
            template.expire(LockIPkey,LOCK_IP_EXPIRE_SECONDS, TimeUnit.HOURS);
            return "您已经访问超过3次";
        }else {
            return ""; }
    }

}
