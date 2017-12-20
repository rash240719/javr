import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class JRabbitGUI extends JRabbit {

    public void openSendDialog() {
        String message = JOptionPane.showInputDialog(
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
        openOperationSelectionDialog(this::openSendOperationDialog);
    }

    public void openOperationSelectionDialog(Consumer<Character> handler) {
        connectRemote("operations");
        String[] options = {"Addition",
                "Subtraction",
                "Product",
                "Division",
                "Go back"};
        int choice = 0;

        while (choice != options.length - 1) {
            char operator = ' ';
            ArrayList choiceResults = getChoice(options, choice);
            choice = (int) choiceResults.get(0);
            operator = (char) choiceResults.get(1);

            if (choice < options.length - 1) handler.accept(operator);
        }
    }

    public ArrayList getChoice(String[] options, int choice) {
        ArrayList list = new ArrayList();
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

        list.add(choice);
        list.add(operator);

        return list;
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
