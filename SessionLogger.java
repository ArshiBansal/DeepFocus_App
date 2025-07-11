import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class SessionLogger {

    private static final String LOG_FILE = "logs.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void logCycleCompletion() {
        String today = dateFormat.format(new Date());

        Map<String, Integer> logMap = readLogs();
        logMap.put(today, logMap.getOrDefault(today, 0) + 1);
        writeLogs(logMap);
    }

    public static String getTodayLog() {
        String today = dateFormat.format(new Date());
        Map<String, Integer> logMap = readLogs();
        return today + ": " + logMap.getOrDefault(today, 0) + " Focus Cycles\n";
    }

    private static Map<String, Integer> readLogs() {
        Map<String, Integer> logMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    logMap.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        } catch (IOException e) {
            // First time run or file doesn't exist
        }
        return logMap;
    }

    private static void writeLogs(Map<String, Integer> logMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE))) {
            for (Map.Entry<String, Integer> entry : logMap.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing log file.");
        }
    }
}
