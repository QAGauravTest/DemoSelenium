package pages;

import SeleniumUtils.SeleniumUtil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class AmazonHomePage extends BaseClass {

    @FindBy(xpath = "//*[@id= 'twotabsearchtextbox']")
    WebElement searchBox;

    @FindBy(xpath = "//span[@class = 'a-size-medium a-color-base a-text-normal']")
    List<WebElement> productNames;

    @FindBy(xpath = "//span[@class = 'a-price-whole']")
    List<WebElement> priceList;

    @FindBy(xpath = "//a")
    List<WebElement> ATag;

    public AmazonHomePage(WebDriver wdriver) {
        super(wdriver);
    }

    public void SearchProducts(String product)
    {
        SeleniumUtil.enterText(this.getDriver(),searchBox,product);
        searchBox.sendKeys(Keys.ENTER);
    }
    public NavigableMap<String,String> getListOfProductNames()
    {
        NavigableMap<String,String > Model_Price = new TreeMap<String,String>() ;

        for(WebElement price : priceList)
        {
            String model = productNames.get(priceList.indexOf(price)).getText();
            Model_Price.put(   model , price.getText() ) ;
        }

        return Model_Price.descendingMap() ;
    }

    public void verifyBrokenLink(String Ecom) throws Exception {
        SeleniumUtil.verifyBrokenLink(ATag , Ecom );
    }


}
