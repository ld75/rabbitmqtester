package org.rabbitmqclient;

import org.junit.jupiter.api.Test;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqClientTest {


    @Test
    public void sendMessage()
    {
        String queuename = "hello";
        String hostname = "localhost";
        int port = 5672;
        String message = "Hello World!";
        RabbitMqClient rabbitMqClient = new RabbitMqClient();
        rabbitMqClient.sendmessage(hostname, port, queuename, message);

    }


}
