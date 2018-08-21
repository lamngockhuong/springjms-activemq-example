package com.ngockhuong.jms;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;

@Component
public class Listener {

    @Autowired
    private Producer producer;

    @JmsListener(destination = "inbound.queue")
    public void receiveMessage(Message jsonMessage) throws JMSException {
        String messageData = null;
        System.out.println("Received message " + jsonMessage);
        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            messageData = textMessage.getText();
        }

        producer.sendMessage("outbound.queue", messageData);
    }

//    @JmsListener(destination = "inbound.queue")
//    @SendTo("outbound.queue")
//    public String receiveMessageFromTopic(final Message jsonMessage) throws JMSException {
//        String messageData = null;
//        System.out.println("Received message " + jsonMessage);
//        String response = null;
//        if(jsonMessage instanceof TextMessage) {
//            TextMessage textMessage = (TextMessage)jsonMessage;
//            messageData = textMessage.getText();
//            Map map = new Gson().fromJson(messageData, Map.class);
//            response  = "Hello " + map.get("name");
//        }
//        return response;
//    }
}
