package uk.co.boothen.cloud.stream;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

@Configuration
@EnableFeignClients
public class ApplicationConfiguration {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Europe/London")));
    }

    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Jdk8Module jdk8Module() {
        return new Jdk8Module();
    }

    @Bean
    public SimpleModule additionalSerializers() {
        return new SimpleModule();
    }
}
