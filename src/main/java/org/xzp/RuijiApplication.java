package org.xzp;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching

public class RuijiApplication
{
    public static void main( String[] args )
    {
        SpringApplication application = new SpringApplication(RuijiApplication.class);
        application.setAllowBeanDefinitionOverriding(true);
        application.run(args);
    }
}
