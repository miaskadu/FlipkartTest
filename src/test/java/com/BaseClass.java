package com;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import utility.ScreenShot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    public WebDriver driver;
    static Properties properties;

    public BaseClass()
    {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+ File.separator+"browser.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void openBrowser()
    {
        String browserName = properties.getProperty("browser");
        if(browserName.contentEquals("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            System.setProperty("webdriver.chrome.silentOutput", "true");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
        else if(browserName.contentEquals("firefox"))
        {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        }

    }

    @AfterMethod
    public void closeBrowser() {
       new ScreenShot(driver).takeScreenshot();
        driver.quit();
    }
}

