package uk.co.boothen.cloud.stream;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final StreamBridge streamBridge;

    public Producer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendPayload(Integer value) {
        streamBridge.send("producer-out-0",new Payload(value));
    }

}
