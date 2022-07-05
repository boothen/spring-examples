package uk.co.boothen.cloud.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReceivingSource {

    @Input("subscription-channel")
    SubscribableChannel subscriptionChannel();
}
