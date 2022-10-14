package org.xzp.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 16:42
 * @Version 1.0
 */

/**
 * 公共字段填充
 */
@Component
public class MetaObjectHandl implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime",new Date());
        metaObject.setValue("createUser",FillThreadLocal.getvalue());
        metaObject.setValue("updateTime",new Date());
        metaObject.setValue("updateUser",FillThreadLocal.getvalue());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",new Date());
        metaObject.setValue("updateUser",FillThreadLocal.getvalue());
    }
}
