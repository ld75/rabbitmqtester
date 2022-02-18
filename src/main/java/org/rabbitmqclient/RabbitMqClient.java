package org.rabbitmqclient;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqClient {
    public void sendmessage(String hostname, int port, String QUEUE_NAME, String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(port);
        factory.setHost(hostname);
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel())
        {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
