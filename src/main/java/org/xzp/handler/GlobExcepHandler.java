package org.xzp.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xzp.common.R;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 15:34
 * @Version 1.0
 */

/**
 * 全局异常处理类
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobExcepHandler {

//    sql异常
    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public R<String> controllerExcepHandler(SQLIntegrityConstraintViolationException msg){
        log.error(msg.getMessage());
        if(msg.getMessage().contains("Duplicate entry")) {
            String[] s = msg.getMessage().split(" ");
            String s1 = s[2] + "已存在";
            return R.error(s1);
        }
        return R.error("操作异常！");
    }
}
