package uk.co.boothen.cloud.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;

public interface ReceivingSource {

    @Input("subscription-channel")
    PollableMessageSource pollableMessageSource();
}
