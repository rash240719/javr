import com.google.gson.Gson;
import org.renjin.sexp.SEXP;

import javax.swing.*;
import java.awt.*;

public class S3GUI extends S3 {

    public void openOperationSelectionDialog() {
        new JRabbitGUI().openOperationSelectionDialog(operator -> {
            openUploadOperationDialog(operator);
        });
    }

    public void openUploadOperationDialog(char operator) {
        Object[] values = new RGUI().openBinaryOperationDialog();
        JRabbitGUI operationsJRabbitGUI = new JRabbitGUI();
        operationsJRabbitGUI.connectRemote("operations");

        if (values != null) {
            operationsJRabbitGUI.sendOperation(values, operator);
            receiveResults();
        }
    }

    public void receiveOperations() {
        JRabbitGUI operationsJRabbitGUI = new JRabbitGUI();
        operationsJRabbitGUI.connectRemote("operations");
        JRabbitGUI resultsJRabbitGUI = new JRabbitGUI();
        resultsJRabbitGUI.connectRemote("results");
        operationsJRabbitGUI.receive((String received) -> {
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

            String filename = "results/result-" + System.currentTimeMillis();
            writeFileFromString(filename, String.valueOf(result));
            uploadFile(filename);
            deleteFile(filename);
            operation.setResult(filename);

            String result_json = gson.toJson(operation, Operation.class);

            resultsJRabbitGUI.sendWithoutClosing(result_json, (String sent) -> {
                System.out.println(" [x] Uploaded result: '" + sent + "'");
            });
        });
    }

    public void receiveResults() {
        JRabbitGUI jRabbitGUI = new JRabbitGUI();
        jRabbitGUI.connectRemote("results");
        jRabbitGUI.receiveOnce((String received) -> {
            Operation operation;

            System.out.printf(" [x] Downloaded result: '%s'%n", received);

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

            boolean downloaded = false;

            while (!downloaded) {
                try {
                    message += downloadFileAsString(String.valueOf(operation.getResult()));
                    downloaded = true;
                } catch (Exception e) {
                    downloaded = false;
                }
            }

            JOptionPane.showMessageDialog(new Frame(), message);
        });
    }
}
