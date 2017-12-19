import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.renjin.sexp.SEXP;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.function.Consumer;

public class JRabbit {
    private String QUEUE_NAME;
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public void connectRemote(String QUEUE_NAME) {
        this.QUEUE_NAME = QUEUE_NAME;
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

    protected void send(String message, Consumer<String> handler) {
        try {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            handler.accept(message);
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sendWithoutClosing(String message, Consumer<String> handler) {
        try {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            handler.accept(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void receive(Consumer<String> handler) {
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

    protected void receiveOnce(Consumer<String> handler) {
        try {
            System.out.println("Awaiting messages");
            GetResponse response = channel.basicGet(QUEUE_NAME, true);

            if (response != null) {
                String message = new String(response.getBody(), "UTF-8");

                handler.accept(message);
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

        send(json, (String sent) -> {
            System.out.println(" [x] Sent operation: '" + sent + "'");
        });
    }

    public void receiveOperations() {
        connectRemote("operations");
        JRabbit results_connection = new JRabbit();
        results_connection.connectRemote("results");
        receive((String received) -> {
            System.out.printf(" [x] Received operation: '%s'%n", received);

            RIntermediary r = new RIntermediary();
            Gson gson = new Gson();
            Operation operation = gson.fromJson(received, Operation.class);

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

            results_connection.sendWithoutClosing(result_json, (String sent) -> {
                System.out.println(" [x] Sent result: '" + sent + "'");
            });
        });
    }

    public void receiveResults() {
        connectRemote("results");
        receiveOnce((String received) -> {
            Operation operation;

            System.out.printf(" [x] Received result: '%s'%n", received);

            RIntermediary r = new RIntermediary();
            Gson gson = new Gson();
            operation = gson.fromJson(received, Operation.class);
            String message = "";

            message += operation.getX();
            message += " ";
            message += operation.getOperator();
            message += " ";
            message += operation.getY();
            message += " = ";
            message += operation.getResult();

            JOptionPane.showMessageDialog(new Frame(), message);
        });
    }

    public String getQueueName() {
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
