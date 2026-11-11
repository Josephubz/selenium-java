package tests;


import core.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Path;
import java.time.Duration;

public abstract class BaseTest {

    protected BaseTest() {}

    private static final ScreenshotManager SCREENSHOT_MANAGER =
            new ScreenshotManager(Path.of(
                    ConfigReader.getString("screenshotDirectory", "target/screenshots")
            ));

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        WebDriver driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);
        getDriver().get(ConfigReader.getString("url", ""));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            captureFailureScreenshot(result);
        }

        DriverManager.quitDriver();
    }

    private void captureFailureScreenshot(ITestResult result) {

        String testClass = result.getTestClass()
                .getRealClass()
                .getSimpleName();

        String testMethod = result.getMethod()
                .getMethodName();

        try {
            SCREENSHOT_MANAGER.captureViewport(
                    getDriver(),
                    testClass,
                    testMethod
            );
        } catch (Exception e) {
            throw new IllegalStateException("Screenshot capture failed", e);
        }
    }

    protected static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected static WaitManager createWaitManager() {
        return new WaitManager(getDriver());
    }

    protected static WaitManager createWaitManager(long timeout, long pollingInterval) {
        return new WaitManager(
                getDriver(),
                Duration.ofSeconds(timeout),
                Duration.ofMillis(pollingInterval)
        );
    }

}
