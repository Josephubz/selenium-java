package core;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public final class WaitManager {

    private static final Duration DEFAULT_TIMEOUT = ConfigReader.getDurationSeconds("explicitWait", 15);
    private static final Duration DEFAULT_POLLING = ConfigReader.getDurationMillis("pollingInterval", 500);

    private final WebDriverWait wait;


    public WaitManager(WebDriver driver) {
        this(
                driver,
                DEFAULT_TIMEOUT,
                DEFAULT_POLLING
        );
    }

    public WaitManager(WebDriver driver, Duration timeout, Duration pollingInterval ) {
        Objects.requireNonNull(driver, "WebDriver cannot be null!");

        validateTimeouts(timeout, "Timeout");
        validateTimeouts(pollingInterval, "Polling interval");

        wait = new WebDriverWait(driver, timeout, pollingInterval);
    }

    private static void validateTimeouts(Duration duration, String name) {
        Objects.requireNonNull(duration, name + " cannot be null!");

        if (duration.isZero() || duration.isNegative()) {
            throw new IllegalArgumentException(name +  " cannot be negative!");
        }
    }

    public WebElement untilVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement untilClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void untilInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public List<WebElement> untilAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public Alert untilAlertPresent() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void untilTitleContains(String text) {
        wait.until(ExpectedConditions.titleContains(text));
    }

}
