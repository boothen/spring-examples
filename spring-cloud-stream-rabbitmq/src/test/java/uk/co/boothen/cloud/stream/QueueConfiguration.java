package uk.co.boothen.cloud.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class QueueConfiguration {

    @Bean
    public BlockingQueue<Payload> integerList() {
        return new LinkedBlockingQueue<>();
    }
}
