import com.rabbitmq.client.*;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class JRabbit {
    private final static String QUEUE_NAME = "testing";

    public void openSendDialog() {
        String message = (String) JOptionPane.showInputDialog(
                new Frame(),
                "Input your message:\n",
                "Sending a message",
                JOptionPane.PLAIN_MESSAGE);

        if (message != null && message.length() > 0) {
            send(message);
        }
    }

    private void send(String message) {
        ConnectionFactory factory;
        Connection connection;
        Channel channel;

        try {
            factory = new ConnectionFactory();
            factory.setUri(new URI("amqp://bbofzllq:aYr9P_WTYo7SmwLj6fuzfedosZ-4jFfG@elephant.rmq.cloudamqp.com/bbofzllq"));
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printReceivedMessages() {
        ConnectionFactory factory;
        Connection connection;
        Channel channel;

        try {
            factory = new ConnectionFactory();
            factory.setUri(new URI("amqp://bbofzllq:aYr9P_WTYo7SmwLj6fuzfedosZ-4jFfG@elephant.rmq.cloudamqp.com/bbofzllq"));
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Awaiting messages");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String message = new String(body, "UTF-8");
                        System.out.println(" [x] Received '" + message + "'");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
