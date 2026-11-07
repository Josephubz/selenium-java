
package core;

import org.openqa.selenium.*;

import java.util.Objects;

public abstract class BasePage {

    protected final WebDriver driver;
    private final WaitManager waitManager;
//    private final JavascriptExecutor js;


    protected BasePage(WebDriver driver, WaitManager waitManager) {
        this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null");
        this.waitManager = Objects.requireNonNull(waitManager, "WaitManager cannot be null");
    }


    protected WebElement waitForVisible(By locator) {
        return waitManager.untilVisible(locator);
    }

    protected WebElement waitForClickable(By locator) {
        return waitManager.untilClickable(locator);
    }

    protected void waitForInvisibility(By locator) {
        waitManager.untilInvisible(locator);
    }

    protected void click(By locator) {
        waitForClickable(locator).click();
    }

//    protected void jsClick(By locator) {
//        WebElement element = waitForVisible(locator);
//        js.executeScript("arguments[0].click();", element);
//    }

    protected void type(By locator, String text) {
        Objects.requireNonNull(text, "Input text cannot be null");

        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }


    protected void clear(By locator) {
        waitForVisible(locator).clear();
    }


    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    protected String getAttribute(By locator, String attributeName) {
        return waitForVisible(locator).getAttribute(attributeName);
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected boolean isVisible(By locator) {
        try {
            waitForVisible(locator);
            return true;

        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        return waitForVisible(locator).isEnabled();
    }

    protected void acceptAlert() {
        waitManager.untilAlertPresent().accept();
    }

}
