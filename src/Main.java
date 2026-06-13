package simpos;

import simpos.ui.LoginFrame;
import simpos.util.Database;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Database.initDatabase();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
