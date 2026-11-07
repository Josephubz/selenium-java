package tests;

import core.ConfigReader;
import org.testng.Assert;
import pages.DashboardPage;
import pages.LoginPage;

import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test (priority = 1)
    public void successfulLoginTest() {

        LoginPage loginPage = new LoginPage(getDriver(), createWaitManager());

        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        DashboardPage dashboardPage = new DashboardPage(getDriver(), createWaitManager());
        Assert.assertTrue(dashboardPage.isDashboardVisible(), "Dashboard is not visible");

    }

    @Test
    public void invalidLoginTest() {

//        getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        LoginPage loginPage = new LoginPage(getDriver(), createWaitManager());

        loginPage.login("test@example.com", "password123");

        String expectedErrorMessage = "Invalid credentials";
        String actualErrorMessage= loginPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Login Error");
    }

    @Test
    public void errorMessageVisibilityTest() {

//        getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        LoginPage loginPage = new LoginPage(getDriver(), createWaitManager());

        loginPage.login("", "");
        String actualErrorMessage = loginPage.getInputErrorMessage();
        Assert.assertEquals(actualErrorMessage, "Required",  "Error message not matches");

    }

}
