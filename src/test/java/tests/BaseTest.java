package tests;


import core.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseTest {

    protected BaseTest() {}

    @BeforeMethod
    public void setUp() {
        WebDriver driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);
        getDriver().get(ConfigReader.getString("url", ""));
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
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
