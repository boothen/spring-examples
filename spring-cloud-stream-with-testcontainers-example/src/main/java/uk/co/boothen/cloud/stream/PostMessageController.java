package uk.co.boothen.cloud.stream;


import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post-message")
public class PostMessageController {

    private final MessageChannel messageChannel;

    public PostMessageController(SendingSource sendingSource) {
        messageChannel = sendingSource.channel();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public void findCustomerMatches() {
        Message<Payload> message = MessageBuilder.withPayload(new Payload(10))
                                                 .build();
        messageChannel.send(message);
    }
}
