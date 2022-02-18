import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TestStandaloneClass {
    public static void main(String... args)
    {
        ConnectionFactory factory = new ConnectionFactory();
        System.out.println("HELLO WORLD");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel())
        {
            channel.queueDeclare("queue", false, false, false, null);
            channel.basicPublish("", "queue", null, "test".getBytes());
            System.out.println(" [x] Sent '");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
