import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerLogic {

    private Timer timer;
    private int remainingSeconds;
    private int totalFocusCount = 0;
    private int cycleCounter = 0;

    private boolean isRunning = false;
    private boolean isFocusSession = true;
    private boolean isLongBreak = false;

    // UI references
    private JLabel timerLabel;
    private JLabel sessionLabel;
    private JLabel cycleLabel;
    private JPanel timerPanel;

    public TimerLogic(JLabel timerLabel, JLabel sessionLabel, JLabel cycleLabel, JPanel timerPanel) {
        this.timerLabel = timerLabel;
        this.sessionLabel = sessionLabel;
        this.cycleLabel = cycleLabel;
        this.timerPanel = timerPanel;

        setSession("Focus");
    }

    private void setSession(String type) {
        switch (type) {
            case "Focus":
                remainingSeconds = 90 * 60;
                isFocusSession = true;
                isLongBreak = false;
                sessionLabel.setText("Session: Focus");
                timerPanel.setBackground(Color.decode("#1B2A41"));
                timerLabel.setForeground(Color.WHITE);
                break;

            case "Short Break":
                remainingSeconds = 15 * 60;
                isFocusSession = false;
                isLongBreak = false;
                sessionLabel.setText("Session: Short Break");
                timerPanel.setBackground(Color.decode("#F4F4F4"));
                timerLabel.setForeground(Color.BLACK);
                break;

            case "Long Break":
                remainingSeconds = 30 * 60;
                isFocusSession = false;
                isLongBreak = true;
                sessionLabel.setText("Session: Long Break");
                timerPanel.setBackground(Color.decode("#F7E7CE"));
                timerLabel.setForeground(Color.BLACK);
                break;
        }

        updateTimerLabel();
    }

    public void start() {
        if (isRunning) return;

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                    updateTimerLabel();
                } else {
                    timer.stop();
                    isRunning = false;
                    handleSessionEnd();
                }
            }
        });

        timer.start();
        isRunning = true;
    }

    public void pause() {
        if (timer != null && isRunning) {
            timer.stop();
            isRunning = false;
        }
    }

    public void reset() {
        if (timer != null) {
            timer.stop();
        }
        isRunning = false;

        if (isFocusSession) {
            setSession("Focus");
        } else if (isLongBreak) {
            setSession("Long Break");
        } else {
            setSession("Short Break");
        }
    }

    private void handleSessionEnd() {
        if (isFocusSession) {
            totalFocusCount++;
            cycleCounter++;
            cycleLabel.setText("🔁 Focus Cycles: " + cycleCounter + "/2");

            // ✅ Log completed focus session to logs.txt
            SessionLogger.logCycleCompletion();

            if (cycleCounter >= 2) {
                cycleCounter = 0;
                setSession("Long Break");
            } else {
                setSession("Short Break");
            }

        } else {
            setSession("Focus");
        }

        JOptionPane.showMessageDialog(null, "Session complete!", "DeepFocus", JOptionPane.INFORMATION_MESSAGE);
        start(); // auto-start next session
    }

    private void updateTimerLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
