package stenden.spring.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import stenden.spring.resource.Message;

@Component
public class JmsTest {

    @JmsListener(destination = "stenden.mq.example")
    public void handleMessage(Message message) {
        System.out.println("MESSAGE RECEIVED: " + message.getMessage());
    }

}
