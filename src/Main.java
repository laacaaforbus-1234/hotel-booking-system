import ui.LoginUI;

public class Main {
    public static void main(String[] args) {
        // Start the login UI
        javax.swing.SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}