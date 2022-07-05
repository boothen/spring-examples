package uk.co.boothen.cloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

@Configuration
public class TestConfiguration {

    @Autowired
    private BlockingQueue<Payload> integerList;


    @Bean
    public Consumer<Message<Payload>> consumer() {
        return message -> integerList.add(message.getPayload());
    }
    
}
