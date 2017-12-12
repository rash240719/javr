import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String... args) {
        try {
            JRabbitGUI jRabbitGUI = new JRabbitGUI();
            String[] options = {"Send",
                    "Receive",
                    "Say hello from r script",
                    "Operate Arithmetics",
                    "Sleep -.-zzz"};
            int choice = 0;
            RGUI rgui = new RGUI();

            while (choice != options.length - 1 && choice != -1) {
                choice = JOptionPane.showOptionDialog(new Frame(),
                        "What will we do today :D ?",
                        "JRabbit",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[options.length - 1]);

                switch (choice) {
                    case 0:
                        System.out.println("You chose " + options[choice]);
                        jRabbitGUI.openSendDialog();
                        break;

                    case 1:
                        System.out.println("You chose " + options[choice]);
                        jRabbitGUI.printReceivedMessages();
                        break;

                    case 2:
                        System.out.println("You chose " + options[choice]);
                        rgui.sayHello("Hello");
                        break;

                    case 3:
                        System.out.println("You chose " + options[choice]);
                        rgui.openOperationsDialog();
                        break;

                    default:
                        if (choice != -1) System.out.println("You chose " + options[choice]);
                        System.out.println("Good night");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
