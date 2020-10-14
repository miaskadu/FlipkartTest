package utility;

import com.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {

    private int waitTime = 30;
    WebDriver driver;
    WebDriverWait webDriverWait;

    public Wait(WebDriver driver)
    {
        this.driver=driver;
        webDriverWait = new WebDriverWait(driver, waitTime);
    }

    public WebElement waitAndClick(WebElement element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        return element;
    }

    public Boolean waitUntilIsPresent(By by) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return null;
    }
}
