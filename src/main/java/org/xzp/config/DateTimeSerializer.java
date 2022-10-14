package org.xzp.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 17:44
 * @Version 1.0
 */
@Configuration
public class DateTimeSerializer {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;
        @Bean
        public Jackson2ObjectMapperBuilderCustomizer addDateTimeFormatter() {
            return builder -> {
                DateTimeFormatter dt = ofPattern(pattern), d = ofPattern(pattern), t = ofPattern("HH:mm:ss");

                builder.simpleDateFormat(pattern)
                        .deserializers(new LocalDateTimeDeserializer(dt), new LocalDateDeserializer(d), new LocalTimeDeserializer(t))
                        .serializers(new LocalDateTimeSerializer(dt), new LocalDateSerializer(d), new LocalTimeSerializer(t));
            };
        }

}
