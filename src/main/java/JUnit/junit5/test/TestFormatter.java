package JUnit.junit5.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class TestFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = format.format(new Date(record.getMillis())); // 格式化日志时间
        return time + " : " + record.getMessage() + "\n";
    }
}
