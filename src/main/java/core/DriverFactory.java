
package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.Dimension;


public final class DriverFactory {

    private DriverFactory() {}

    public static WebDriver createDriver() {

        String browser = ConfigReader.getString("browser", "chrome");
        boolean headless = ConfigReader.getBoolean("headless", false);

        WebDriver driver = switch (browser) {
            case "chrome" -> createChromeDriver(headless);
            case "firefox" -> createFirefoxDriver(headless);
            case "edge" -> createEdgeDriver(headless);
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        };

        configureDriver(driver, headless);
        return driver;
    }


    private static WebDriver createChromeDriver(boolean headless) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");

        if (headless) {
            options.addArguments("--headless=new");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.webnotifications.serviceworker.enabled", false);
        options.addPreference("dom.push.enabled", false);

        if (headless) {
            options.addPreference("", "");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {

        EdgeOptions options = new EdgeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        return new EdgeDriver(options);
    }


    private static void configureDriver(WebDriver driver, boolean headless) {

        int width = ConfigReader.getInt("width", 1920);
        int height = ConfigReader.getInt("height", 1080);

        driver.manage().timeouts()
                .pageLoadTimeout(ConfigReader.getDurationSeconds("pageLoadTimeout", 60));

        driver.manage().timeouts()
                .scriptTimeout(ConfigReader.getDurationSeconds("scriptTimeout", 30));

        if (headless) {
            driver.manage().window().setSize(new Dimension(width, height));
        } else {

            driver.manage().window().maximize();
        }
    }
}
