import org.renjin.sexp.SEXP;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class JRabbitGUI extends JRabbit {

    public void openSendDialog() {
        String message = (String) JOptionPane.showInputDialog(
                new Frame(),
                "Input your message:\n",
                "Sending a message",
                JOptionPane.PLAIN_MESSAGE);

        if (message != null && message.length() > 0) {
            send(message, (String sent) -> {
                System.out.println(" [x] Sent message: '" + sent + "'");
            });
        }
    }

    public void printReceivedMessages() {
        super.receive((String received) -> {
            System.out.printf(" [x] Received '%s'%n", received);
        });
    }

    public void openOperationSelectionDialog() {
        connectRemote("operations");
        String[] options = {"Addition",
                "Subtraction",
                "Product",
                "Division",
                "Go back"};
        int choice = 0;

        while (choice != options.length - 1) {
            choice = JOptionPane.showOptionDialog(new Frame(),
                    "What operation will we do today :D ?",
                    "Operation selection",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[options.length - 1]);
            char operator = ' ';

            switch (choice) {
                case 0:
                    System.out.println("You chose " + options[choice]);
                    operator = '+';
                    break;

                case 1:
                    System.out.println("You chose " + options[choice]);
                    operator = '-';
                    break;

                case 2:
                    System.out.println("You chose " + options[choice]);
                    operator = '*';
                    break;

                case 3:
                    System.out.println("You chose " + options[choice]);
                    operator = '/';
                    break;

                default:
                    System.out.println("You chose " + options[choice]);
                    break;
            }

            if (choice < options.length - 1) openSendOperationDialog(operator);
        }
    }

    public void openSendOperationDialog(char operator) {
        RGUI rgui = new RGUI();
        Object[] values = rgui.openBinaryOperationDialog();
        connectRemote("operations");
        JRabbit results_connection = new JRabbit();
        results_connection.connectRemote("results");

        if (values != null) {
            sendOperation(values, operator);
            results_connection.receiveResults();
        }
    }
}
