
package pages;

import core.BasePage;

import core.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameInput =
            By.name("username");

    private final By passwordInput =
            By.name("password");

    private final By loginButton =
            By.xpath("//button[@type='submit']");

    private final By errorMessage = By.xpath("//div[@role='alert']");

    private final By inputErrorMessage =
            By.xpath("//input[@name='password']/following::span[text()='Required']");


    public LoginPage(WebDriver driver, WaitManager waitManager) {
        super(driver, waitManager);
    }


    public void enterUsername(String email) {
        type(usernameInput, email);
    }


    public void enterPassword(String password) {
        type(passwordInput, password);
    }


    public void clickLogin() {
        click(loginButton);
    }


    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public String getInputErrorMessage() {
        return getText(inputErrorMessage);
    }


    public void login(String email, String password) {
        driver.getTitle();
        enterUsername(email);
        enterPassword(password);
        clickLogin();
    }
}
