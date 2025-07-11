import java.awt.*;
import javax.swing.*;

public class DeepFocus {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DeepFocus().createAndShowGUI();
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("DeepFocus - Focus Cycle App ⏳");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new GridLayout(1, 2)); // Left and Right panel

        // ----- Left Panel (Focus Timer) -----
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftPanel.setBackground(Color.decode("#1B2A41"));

        JLabel timerLabel = new JLabel("01:30:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 48));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setOpaque(false);

        JLabel sessionLabel = new JLabel("Session: Focus", SwingConstants.CENTER);
        sessionLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        sessionLabel.setForeground(Color.WHITE);
        sessionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cycleLabel = new JLabel("🔁 Focus Cycles: 0/2", SwingConstants.CENTER);
        cycleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cycleLabel.setForeground(Color.WHITE);
        cycleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton startBtn = new JButton("Start");
        JButton pauseBtn = new JButton("Pause");
        JButton resetBtn = new JButton("Reset");
        controlPanel.add(startBtn);
        controlPanel.add(pauseBtn);
        controlPanel.add(resetBtn);
        controlPanel.setOpaque(false);

        leftPanel.add(timerLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(sessionLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(cycleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(controlPanel);

        // ----- Right Panel (Task List + Logs) -----
        TaskPanel taskPanel = new TaskPanel();

        // ----- Logic Connection -----
        TimerLogic timerLogic = new TimerLogic(timerLabel, sessionLabel, cycleLabel, leftPanel);
        startBtn.addActionListener(e -> timerLogic.start());
        pauseBtn.addActionListener(e -> timerLogic.pause());
        resetBtn.addActionListener(e -> timerLogic.reset());

        // Add everything to the frame
        frame.add(leftPanel);
        frame.add(taskPanel);

        frame.setVisible(true);
    }
}
