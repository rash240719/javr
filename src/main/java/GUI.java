import javax.swing.*;
import java.awt.*;

public class GUI {
    public void showMainMenu() {
        try {
            String[] options = {"Local version",
                    "Distributed version",
                    "S3 version",
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

                    case 2:
                        System.out.println("You chose " + options[choice]);
                        showS3Menu();
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
                    "Send file",
                    "Download file",
                    "Go back"};
            int choice = 0;
            RGUI rgui = new RGUI();
            S3 s3 = new S3();
            String filename;

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

                    case 4:
                        System.out.println("You chose " + options[choice]);
                        filename = JOptionPane.showInputDialog(
                                new Frame(),
                                "Input your filename with extension:\n",
                                "Uploading a file",
                                JOptionPane.PLAIN_MESSAGE);
                        if (!filename.isEmpty()) s3.uploadFile(filename);
                        break;

                    case 5:
                        System.out.println("You chose " + options[choice]);
                        filename = JOptionPane.showInputDialog(
                                new Frame(),
                                "Input your filename with extension:\n",
                                "Downloading a file",
                                JOptionPane.PLAIN_MESSAGE);
                        s3.downloadAndWriteFile(filename);
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

    public void showS3Menu() {
        try {
            S3 s3 = new S3();
            /*String[] options = {"Operate Arithmetic",
                    "Receive operations",
                    "Go back"};*/
            String[] options = {"Send file",
                    "Go back"};
            int choice = 0;

            while (choice != options.length - 1 && choice != -1) {
                choice = JOptionPane.showOptionDialog(new Frame(),
                        "What will we do today :D ?",
                        "S3 Version Menu",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[options.length - 1]);

                switch (choice) {
                    case 0:
                        System.out.println("You chose " + options[choice]);
                        s3.uploadFile("tamalongo");
                        break;

                    case 1:
                        System.out.println("You chose " + options[choice]);
                        //jRabbitGUI.receiveOperations();
                        System.out.println("Work in progress :D");
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
