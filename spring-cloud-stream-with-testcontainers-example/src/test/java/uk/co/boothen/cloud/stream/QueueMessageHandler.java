package uk.co.boothen.cloud.stream;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class QueueMessageHandler implements MessageHandler {

    private final BlockingQueue<Payload> integerList;

    public QueueMessageHandler(BlockingQueue<Payload> integerList) {
        this.integerList = integerList;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
//        StaticMessageHeaderAccessor.getAcknowledgmentCallback(message).noAutoAck();
        var payload = (Payload) message.getPayload();
        integerList.add(payload);
    }
}