package uk.co.boothen.cloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;

@Configuration
public class TestConfiguration {

    @Autowired
    private BlockingQueue<Payload> integerList;

//    @StreamListener(target = "subscription-channel")
//    public void routeValue(Payload integer) {
//        integerList.add(integer);
//    }
    
}
