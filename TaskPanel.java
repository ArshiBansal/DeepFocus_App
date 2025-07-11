import java.awt.*;
import javax.swing.*;

public class TaskPanel extends JPanel {

    private DefaultListModel<String> taskModel;
    private JList<String> taskList;
    private JTextArea sessionLog;

    public TaskPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ---- Task Section ----
        JLabel taskLabel = new JLabel("Your Focus Tasks");
        taskLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane taskScrollPane = new JScrollPane(taskList);

        JButton addTaskBtn = new JButton("+ Add Task");
        addTaskBtn.addActionListener(e -> {
            new AddTaskDialog(SwingUtilities.getWindowAncestor(this), taskModel).setVisible(true);
        });

        JPanel taskHeader = new JPanel(new BorderLayout());
        taskHeader.add(taskLabel, BorderLayout.WEST);
        taskHeader.add(addTaskBtn, BorderLayout.EAST);

        JPanel taskPanel = new JPanel(new BorderLayout(5, 5));
        taskPanel.add(taskHeader, BorderLayout.NORTH);
        taskPanel.add(taskScrollPane, BorderLayout.CENTER);

        // ---- Session Log Section ----
        JLabel logLabel = new JLabel("Focus Cycle Log");
        logLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        sessionLog = new JTextArea(6, 20);
        sessionLog.setEditable(false);
        sessionLog.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // ✅ Get today's log from file
        sessionLog.setText(SessionLogger.getTodayLog());

        JScrollPane logScrollPane = new JScrollPane(sessionLog);

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        logPanel.add(logLabel, BorderLayout.NORTH);
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        // ---- Add to Main Panel ----
        add(taskPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);
    }
}
