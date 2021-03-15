package uk.co.boothen.cloud.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SendingSource {

    @Output("message-channel")
    MessageChannel channel();

}
