package pageobjects;

import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utility.Helpers;
import utility.Wait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlipkartPage {

    WebDriver driver;
    Actions actions;
    Wait wait;

    ArrayList<String> deviceDetailList = new ArrayList<>();
    ArrayList<String> priceList = new ArrayList<>();
    ArrayList<String> ratingsList = new ArrayList<>();

    @FindBy(xpath = "//input[contains(@title,'Search for products')]")
    public WebElement searchProducts;

    @FindBy(xpath = "(//span[text()='Price']/parent::div/parent::div/following-sibling::div//select)[2]")
    public WebElement changePrice;

    @FindBy(xpath = "//div[text()='Price -- Low to High']")
    public WebElement priceLowToHigh;

    @FindBy(xpath = "//div[text()='Availability']")
    public WebElement availability;

    @FindBy(xpath = "//div[text()='Exclude Out of Stock']")
    public WebElement excludeOutOfStock;

    public FlipkartPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }
    public void goTo(String url)
    {
        driver.get(url);
    }

    public void searchProduct()
    {
        actions = new Actions(driver);
        wait = new Wait(driver);
        wait.waitUntilIsPresent(By.xpath("(//span[text()='Login'])[1]"));
        wait.waitAndClick(driver.findElement(By.xpath("//button[text()='✕']")));
        wait.waitAndClick(searchProducts);
        searchProducts.sendKeys("Iphone");
        searchProducts.sendKeys(Keys.ENTER);
        wait.waitUntilIsPresent(By.xpath("//span[text()='Iphone']"));
        wait.waitAndClick(availability);
        wait.waitAndClick(excludeOutOfStock);
        wait.waitUntilIsPresent(By.xpath("//div[text()='Exclude Out of Stock']"));
    }

    public void selectPrice()
    {
        Select select = new Select(changePrice);
        select.selectByVisibleText("₹50000");
        wait = new Wait(driver);
        wait.waitUntilIsPresent(By.xpath("//div[text()='Min-₹50000']"));
        wait.waitAndClick(priceLowToHigh);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addProductsInList() {
        List<WebElement> list = driver.findElements(By.xpath("//a[contains(@href,'apple-iphone')]/div[2]/div[2]/div/div/div[1]"));
        for(int i=1;i<list.size();i++)
        {
            if(Integer.parseInt(Helpers.getNumbersFromString(list.get(i).getText()))<=40000)
            {
                String amount = list.get(i).getText();
                priceList.add(amount);
                String deviceInfo = list.get(i).findElement(By.xpath("./parent::div/parent::div/parent::div/parent::div/div[1]/div")).getText().trim();
                deviceDetailList.add(deviceInfo);
                String ratings = list.get(i).findElement(By.xpath("./parent::div/parent::div/parent::div/parent::div/div/div[2]//span[contains(text(),'Ratings')]")).getText().trim().replaceAll("Ratings","");
                ratingsList.add(ratings);
                System.out.println(deviceInfo+","+amount+","+ratings);
            }
        }
    }

    public void writeDataLineByLine(String filePath)
    {
        // first create file object for file placed at location
        // specified by filepath
        FlipkartPage flipkarPage = new FlipkartPage(driver);
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Device Details", "Price", "Ratings" };
            writer.writeNext(header);

            // add data to csv
            for(int i=0;i<priceList.size();i++)
            {
                String[] data1 = { deviceDetailList.get(i), priceList.get(i), ratingsList.get(i) };
                writer.writeNext(data1);
            }
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
