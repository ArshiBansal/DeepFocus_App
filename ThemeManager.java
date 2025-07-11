import java.awt.*;
import javax.swing.*;

public class ThemeManager {

    private boolean isDarkMode = true;

    public void toggleTheme(JFrame frame) {
        isDarkMode = !isDarkMode;
        SwingUtilities.updateComponentTreeUI(frame);

        for (Component comp : getAllComponents(frame.getContentPane())) {
            applyTheme(comp);
        }

        frame.repaint();
    }

    private void applyTheme(Component comp) {
        if (comp instanceof JPanel || comp instanceof JScrollPane || comp instanceof JDialog) {
            comp.setBackground(isDarkMode ? Color.decode("#1B2A41") : Color.WHITE);
        }
        if (comp instanceof JLabel) {
            comp.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
        }
        if (comp instanceof JButton || comp instanceof JTextField || comp instanceof JTextArea || comp instanceof JList) {
            comp.setBackground(isDarkMode ? new Color(34, 45, 65) : Color.WHITE);
            comp.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
        }
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                applyTheme(child);
            }
        }
    }

    private Component[] getAllComponents(Container c) {
        java.util.List<Component> list = new java.util.ArrayList<>();
        for (Component comp : c.getComponents()) {
            list.add(comp);
            if (comp instanceof Container) {
                Component[] sub = getAllComponents((Container) comp);
                for (Component subComp : sub) {
                    list.add(subComp);
                }
            }
        }
        return list.toArray(new Component[0]);
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }
}
