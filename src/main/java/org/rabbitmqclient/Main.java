package org.rabbitmqclient;

public class Main {
    public static void main (String[] args ) {
    RabbitMqClient rabbitMqClient = new RabbitMqClient();
        String lemessage = args[3];
        String monHostname = args[1];
        int leport= Integer.parseInt(args[0]);
        String laqueueName= args[2];
        rabbitMqClient.sendmessage(monHostname, leport, laqueueName,lemessage);
    }
}
