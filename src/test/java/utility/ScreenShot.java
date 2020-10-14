package utility;

import com.BaseClass;
import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class ScreenShot {

    WebDriver driver;

    public ScreenShot(WebDriver driver)
    {
        this.driver=driver;
    }

    public void takeScreenshot()
    {
        Faker faker = new Faker();
        String fileName = faker.name().firstName().replace("'","");
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator + fileName + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
