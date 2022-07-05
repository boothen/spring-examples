package uk.co.boothen.cloud.stream;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post-message")
public class PostMessageController {

    private final Producer producer;

    public PostMessageController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessage() {
        producer.sendPayload(10);
    }
}
