import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String... args) {
        try {
            GUI gui = new GUI();

            gui.showMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
