package Main;

import java.io.FileWriter;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter("data/audit.csv", true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void writeAction(String action) {
        try {
            fileWriter.append(action);
            fileWriter.append(", ");
            fileWriter.append(dateTimeFormatter.format(java.time.LocalDateTime.now()));
            fileWriter.append("\n");
            fileWriter.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
