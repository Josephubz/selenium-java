package pages;

import core.BasePage;
import core.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    private final By dashboardTitle =  By.xpath("//h6[text()='Dashboard']");

    public DashboardPage(WebDriver driver, WaitManager waitManager) {
        super(driver, waitManager);
    }

    public boolean isDashboardVisible () {
        return isVisible(dashboardTitle);
    }
}
