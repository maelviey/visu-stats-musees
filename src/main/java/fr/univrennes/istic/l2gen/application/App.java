package fr.univrennes.istic.l2gen.application;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | UnsupportedLookAndFeelException e) {
        }

        SwingUtilities.invokeLater(() -> {
            appStatistiquesMusées app = new appStatistiquesMusées();
            app.setVisible(true);
        });
    }
}