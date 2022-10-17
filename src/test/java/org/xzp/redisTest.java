package org.xzp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.xzp.entity.Orders;
import org.xzp.service.OrdersService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/17 10:19
 * @Version 1.0
 */
@SpringBootTest
@Slf4j
public class redisTest {
    @Autowired
    private RedisTemplate<String,Object> template;

    @Autowired
    private OrdersService ordersService;

    @Test
    public void test1(){
        Set<String> keys = template.keys("*");
        for (Object key:
             keys) {
            log.info(key.toString());
        }
    }

    @Test
    public void  test2(){
        String time="2022-10-15 00:00:00";
        String end="2022-10-17 23:00:00";
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.apply(StringUtils.isNotEmpty(time),"UNIX_TIMESTAMP(order_time) >= UNIX_TIMESTAMP('" + time + "')");
        wrapper.apply(StringUtils.isNotEmpty(end),"UNIX_TIMESTAMP(checkout_time) <= UNIX_TIMESTAMP('" + end + "')");
        List<Orders> one = ordersService.list(wrapper);
       log.info(String.valueOf(one.size()));

    }
}
