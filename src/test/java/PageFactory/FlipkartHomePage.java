package PageFactory;

import SeleniumUtils.SeleniumUtil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class FlipkartHomePage extends BaseClass {

    @FindBy(xpath = "//input[@placeholder='Search for Products, Brands and More']")
    WebElement searchBox;

    @FindBy(xpath = "//div[@class = 'col col-7-12']/div[1]")
    List<WebElement> productNames;

    @FindBy(xpath = "//div[@class = 'col col-7-12']/following-sibling::div[1]/div[1]/div[1]/div[1]")
    List<WebElement> priceList;

   @FindBy(xpath = "//a")
    List<WebElement> ATag;

    public FlipkartHomePage(WebDriver wdriver) {
        super(wdriver);
    }

    public void SearchProducts(String product)
    {
        SeleniumUtil.enterText(this.getDriver(),searchBox,product);
        searchBox.sendKeys(Keys.ENTER);
    }
    public NavigableMap getListOfProductNames()
    {
        NavigableMap<String,String > Model_Price = new TreeMap<>() ;

        for(WebElement el : productNames)
        {
            String price = priceList.get(productNames.indexOf(el)).getText().replaceAll("₹","");
            Model_Price.put(  el.getText(), price  ) ;
        }

        return Model_Price.descendingMap() ;
    }

    public void verifyBrokenLink(String Ecom) throws Exception {
        SeleniumUtil.verifyBrokenLink(ATag , Ecom );
    }


}
