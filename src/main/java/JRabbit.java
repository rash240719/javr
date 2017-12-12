import com.rabbitmq.client.*;

import java.net.URI;

public class JRabbit {
    private final static String QUEUE_NAME = "testing";

    protected void send(String message) {
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

    protected void receive(java.util.function.Consumer<String> handler) {
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

            Consumer consumer = createConsumer(channel, handler);

            while (true) {
                channel.basicConsume(QUEUE_NAME, true, consumer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Consumer createConsumer(Channel channel, java.util.function.Consumer<String> handler) {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                try {
                    String message = new String(body, "UTF-8");
                    handler.accept(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        return consumer;
    }
}
