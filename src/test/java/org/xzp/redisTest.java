package org.xzp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Set;

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

    @Test
    public void test1(){
        Set<String> keys = template.keys("*");
        for (Object key:
             keys) {
            log.info(key.toString());
        }
    }
}
