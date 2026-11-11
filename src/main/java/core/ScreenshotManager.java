
package core;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Objects;

public final class ScreenshotManager {

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss-SSS");

    private final Path screenshotDirectory;


    public ScreenshotManager(Path screenshotDirectory) {

        Objects.requireNonNull(
                screenshotDirectory,
                "Screenshot directory cannot be null"
        );

        this.screenshotDirectory =
                screenshotDirectory.toAbsolutePath().normalize();
    }


    public void captureViewport(
            WebDriver driver,
            String testClass,
            String testMethod
    ) throws IOException {

        Objects.requireNonNull(
                driver,
                "WebDriver cannot be null"
        );

        if (!(driver instanceof TakesScreenshot screenshotDriver)) {

            throw new UnsupportedOperationException(
                    "WebDriver does not support screenshots"
            );
        }

        byte[] screenshotBytes =
                screenshotDriver.getScreenshotAs(OutputType.BYTES);


        if (screenshotBytes == null || screenshotBytes.length == 0) {

            throw new IOException(
                    "Screenshot was captured but contains no data"
            );
        }

        // Create class-specific directory
        Path classDirectory = screenshotDirectory.resolve(
                sanitize(testClass)
        );

        Files.createDirectories(classDirectory);

        // Generate unique filename
        String timestamp = LocalDateTime.now()
                .format(TIMESTAMP_FORMAT);

        String fileName =
                sanitize(testMethod)
                        + "_"
                        + timestamp
                        + ".png";

        // Create final screenshot path
        Path destination = classDirectory
                .resolve(fileName)
                .normalize();

        // Ensure destination remains inside screenshot directory
        if (!destination.startsWith(screenshotDirectory)) {

            throw new IOException(
                    "Invalid screenshot destination"
            );
        }

        // Save screenshot bytes
        Files.write(
                destination,
                screenshotBytes,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE
        );

    }


    private String sanitize(String value) {

        if (value == null || value.isBlank()) {
            return "unknown";
        }

        String sanitized = value.replaceAll(
                "[^a-zA-Z0-9._-]",
                "_"
        );

        if (sanitized.equals(".") || sanitized.equals("..")) {
            return "unknown";
        }

        return sanitized;
    }
}
