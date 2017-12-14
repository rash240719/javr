import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.renjin.sexp.SEXP;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.function.Function;

public class JRabbit {
    private final static String QUEUE_NAME = "testing";
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public void connectRemote() {
        factory = new ConnectionFactory();
        try {
            factory.setUri(new URI("amqp://bbofzllq:aYr9P_WTYo7SmwLj6fuzfedosZ-4jFfG@elephant.rmq.cloudamqp.com/bbofzllq"));
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        try {
            System.out.println("Awaiting messages");

            while (true) {
                GetResponse response = channel.basicGet(QUEUE_NAME, true);

                if (response != null) {
                    String message = new String(response.getBody(), "UTF-8");
                    handler.accept(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void receiveOnce(Function<String, Boolean> handler) {
        try {
            System.out.println("Awaiting messages");
            boolean doCycle = true;

            while (doCycle) {
                GetResponse response = channel.basicGet(QUEUE_NAME, true);

                if (response != null) {
                    String message = new String(response.getBody(), "UTF-8");

                    doCycle = handler.apply(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendOperation(Object[] values, char operator) {
        Operation operation = new Operation();
        Gson gson = new Gson();

        operation.setResult(null);
        operation.setOperator(operator);
        operation.setX((double) values[0]);
        operation.setY((double) values[1]);

        String json = gson.toJson(operation, Operation.class);

        send(json);
    }

    public void receiveOperations() {
        receive((String json) -> {
            if (!json.contains("result")) {
                System.out.printf(" [x] Received operation: '%s'%n", json);

                RIntermediary r = new RIntermediary();
                Gson gson = new Gson();
                Operation operation = gson.fromJson(json, Operation.class);

                if (operation.getResult() == null) {
                    Object[] values = {operation.getX(), operation.getY()};
                    SEXP result;

                    switch (operation.getOperator()) {
                        case '+':
                            result = r.operate("Addition", values);
                            break;

                        case '-':
                            result = r.operate("Subtraction", values);
                            break;

                        case '*':
                            result = r.operate("Product", values);
                            break;

                        case '/':
                            result = r.operate("Division", values);
                            break;

                        default:
                            System.out.println("No such operation");
                            result = null;
                            break;
                    }

                    operation.setResult(Double.parseDouble(String.valueOf(result)));

                    String result_json = gson.toJson(operation, Operation.class);

                    send(result_json);
                }
            }
        });
    }

    public void receiveResults() {
        receiveOnce((String json) -> {
            Operation operation;

            if (json.contains("result")) {
                System.out.printf(" [x] Received result: '%s'%n", json);

                RIntermediary r = new RIntermediary();
                Gson gson = new Gson();
                operation = gson.fromJson(json, Operation.class);
                String message = "";

                message += operation.getX();
                message += " ";
                message += operation.getOperator();
                message += " ";
                message += operation.getY();
                message += " = ";
                message += operation.getResult();

                JOptionPane.showMessageDialog(new Frame(), message);

                return false;
            } else {
                return true;
            }
        });
    }

    public static String getQueueName() {
        return QUEUE_NAME;
    }

    public ConnectionFactory getFactory() {
        return factory;
    }

    public void setFactory(ConnectionFactory factory) {
        this.factory = factory;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
