import com.rabbitmq.client.*;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class JRabbitGUI extends JRabbit {
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

    public void printReceivedMessages() {
        super.receive((String message) -> {
            System.out.printf(" [x] Received '%s'%n", message);
        });
    }
}
