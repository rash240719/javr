import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String... args) {
        try {
            JRabbit jRabbit = new JRabbit();

            String[] options = {"Send",
                    "Receive",
                    "Sleep -.-zzz"};
            int choice = JOptionPane.showOptionDialog(new Frame(),
                    "What will we do today :D ?",
                    "JRabbit",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);
            switch (choice) {
                case 0:
                    System.out.println("You chose " + options[choice]);
                    jRabbit.openSendDialog();
                    break;

                case 1:
                    System.out.println("You chose " + options[choice]);
                    jRabbit.printReceivedMessages();
                    break;

                default:
                    System.out.println("You chose " + options[choice]);
                    System.out.println("No such option -_-,");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
