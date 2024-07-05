package utilsFun;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UtilFiles {
    public static Properties loadProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(
                "src/test/resources/ru/levelp/at/" + path)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static String readFile(String path) {
        try {
            return Files.readString(Paths.get("src/test/resources/" + path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
