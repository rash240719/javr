import org.renjin.sexp.SEXP;

import javax.swing.*;
import java.awt.*;

public class RGUI extends R {
    public void openOperationsDialog() {
        String[] options = {"Addition",
                "Subtraction",
                "Product",
                "Division",
                "Go back"};
        int choice = 0;

        while (choice != options.length - 1) {
            choice = JOptionPane.showOptionDialog(new Frame(),
                    "What operation will we do today :D ?",
                    "R",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[options.length - 1]);

            switch (choice) {
                case 0:
                    System.out.println("You chose " + options[choice]);
                    showResult(operateAddition());
                    break;

                case 1:
                    System.out.println("You chose " + options[choice]);
                    showResult(operateSubtraction());
                    break;

                case 2:
                    System.out.println("You chose " + options[choice]);
                    showResult(operateProduct());
                    break;

                case 3:
                    System.out.println("You chose " + options[choice]);
                    showResult(operateDivision());
                    break;

                default:
                    System.out.println("You chose " + options[choice]);
                    break;
            }
        }
    }

    private Object[] openBinaryOperationDialog() {
        Object[] values = new Object[2];

        try {
            values[0] = Double.parseDouble(
                    JOptionPane.showInputDialog(
                            new Frame(),
                            "Input your first value:\n",
                            "Values input",
                            JOptionPane.PLAIN_MESSAGE));

            values[1] = Double.parseDouble(
                    JOptionPane.showInputDialog(
                            new Frame(),
                            "Input your second value:\n",
                            "Values input",
                            JOptionPane.PLAIN_MESSAGE));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return values;
        }
    }

    private void showResult(SEXP result) {
        JOptionPane.showMessageDialog(new Frame(), "Your result is " + result);
    }

    private SEXP operate(String operation) {
        Object[] values = openBinaryOperationDialog();
        SEXP result = null;

        try {
            result = (SEXP) executeScript(operation, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private SEXP operateAddition() {
        return operate("Addition");
    }

    private SEXP operateSubtraction() {
        return operate("Subtraction");
    }

    private SEXP operateProduct() {
        return operate("Product");
    }

    private SEXP operateDivision() {
        return operate("Division");
    }
}
