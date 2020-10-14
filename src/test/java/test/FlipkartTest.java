package test;

import com.BaseClass;
import org.testng.annotations.Test;
import pageobjects.FlipkartPage;
import java.io.File;
import java.util.Date;

public class FlipkartTest extends BaseClass {

    Date date = new Date();
    String fileName = date.toString().replace(":","_").replace(" ","_");

    @Test
    public void getIphoneData()
    {
        FlipkartPage flipkartPage = new FlipkartPage(driver);
        flipkartPage.goTo("https://www.flipkart.com/");
        flipkartPage.searchProduct();
        flipkartPage.selectPrice();
        flipkartPage.addProductsInList();
        flipkartPage.writeDataLineByLine(System.getProperty("user.dir")+ File.separator+"CSVOutput"+File.separator+""+fileName+".csv");
    }
}
