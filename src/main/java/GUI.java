import javax.swing.*;
import java.awt.*;

public class GUI {
    public void showMainMenu() {
        try {
            String[] options = {"Local version",
                    "Distributed version",
                    "Sleep -.-zzz"};
            int choice = 0;

            while (choice != options.length - 1 && choice != -1) {
                choice = JOptionPane.showOptionDialog(new Frame(),
                        "What will we do today :D ?",
                        "Main Menu",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[options.length - 1]);

                switch (choice) {
                    case 0:
                        System.out.println("You chose " + options[choice]);
                        showLocalMenu();
                        break;

                    case 1:
                        System.out.println("You chose " + options[choice]);
                        showDistributedMenu();
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

    public void showLocalMenu() {
        try {
            JRabbitGUI jRabbitGUI = new JRabbitGUI();
            jRabbitGUI.connectRemote("testing");
            String[] options = {"Send",
                    "Receive",
                    "Say hello from r script",
                    "Operate Arithmetic",
                    "Go back"};
            int choice = 0;
            RGUI rgui = new RGUI();

            while (choice != options.length - 1 && choice != -1) {
                choice = JOptionPane.showOptionDialog(new Frame(),
                        "What will we do today :D ?",
                        "Local Version Menu",
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
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDistributedMenu() {
        try {
            JRabbitGUI jRabbitGUI = new JRabbitGUI();
            String[] options = {"Operate Arithmetic",
                    "Receive operations",
                    "Go back"};
            int choice = 0;

            while (choice != options.length - 1 && choice != -1) {
                choice = JOptionPane.showOptionDialog(new Frame(),
                        "What will we do today :D ?",
                        "Distributed Version Menu",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[options.length - 1]);

                switch (choice) {
                    case 0:
                        System.out.println("You chose " + options[choice]);
                        jRabbitGUI.openOperationSelectionDialog();
                        break;

                    case 1:
                        System.out.println("You chose " + options[choice]);
                        jRabbitGUI.receiveOperations();
                        break;

                    default:
                        if (choice != -1) System.out.println("You chose " + options[choice]);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
