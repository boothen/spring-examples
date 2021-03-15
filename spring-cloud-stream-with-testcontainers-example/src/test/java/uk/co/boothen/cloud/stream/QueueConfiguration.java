package uk.co.boothen.cloud.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
@EnableBinding({SendingSource.class, ReceivingSource.class})
public class QueueConfiguration {

    @Bean
    public BlockingQueue<Payload> integerList() {
        return new LinkedBlockingQueue<>();
    }
}
