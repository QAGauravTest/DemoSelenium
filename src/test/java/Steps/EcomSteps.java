package Steps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import PageFactory.AmazonHomePage;
import PageFactory.BaseClass;
import PageFactory.FlipkartHomePage;
import Utils.UpdateExcel;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EcomSteps {
    BaseClass baseClass ;
    FlipkartHomePage flipkartHomePage;
    AmazonHomePage AmazonHomePage;
    boolean isFlipkart = false;
    boolean isAmazon = false;

    @Given("User launches browser {string} and opens {string} URL {string}")
    public void userLaunchesBrowserAndOpensURL(String browser, String ecomm, String url) {

        if(ecomm.contains("Flipkart")){
            baseClass = new BaseClass();
            baseClass.launchBrowserURL(url,browser);
            isFlipkart=true;
        }
        if(ecomm.contains("Amazon")){
            baseClass = new BaseClass();
            baseClass.launchBrowserURL(url,browser);
            isAmazon=true;
        }
    }

    @When("User Searches for product {string}")
    public void userSearchesForProduct(String product) {
       if(isFlipkart)
       {
           flipkartHomePage = new FlipkartHomePage(baseClass.getDriver());
           flipkartHomePage.SearchProducts(product);
       }
      if(isAmazon)
      {
          AmazonHomePage = new AmazonHomePage(baseClass.getDriver());
          AmazonHomePage.SearchProducts(product);
      }

      if(!isAmazon)
        UpdateExcel.clearExistingData();
    }


    @And("Verifies if any broken link is present")
    public void verifiesIfAnyBrokenLinkIsPresent() throws Exception {
        if(isFlipkart)
        {
            flipkartHomePage.verifyBrokenLink("Flipkart");
        }
        if(isAmazon)
        {
            AmazonHomePage.verifyBrokenLink("Amazon");
        }

    }

    @Then("User captures list of prices and exports in Excel")
    public void userCapturesListOfPricesAndExportsInExcel() {
        if(isFlipkart)
           UpdateToExcel(flipkartHomePage.getListOfProductNames(),"Flipkart" );

        if(isAmazon)
            UpdateToExcel(AmazonHomePage.getListOfProductNames(),"Amazon" );

        UpdateExcel.updateExcel(0 ,3, "Least_Price" );
        UpdateExcel.updateExcel(0 ,4, "Ecomm" );
        UpdateExcel.updateExcel(0 ,5, "VERDICT" );
    }

    @And("stores the cheapest price with Ecomm name in Excel")
    public void storesTheCheapestPriceWithEcommNameInExcel() {
        if(isFlipkart)
        {
            Map<String,String> map =  flipkartHomePage.getListOfProductNames();
            List<String> price = new ArrayList<>();
            map.forEach((K,V)->{
                price.add(map.get(K));
            });
            List<String> sortedPrice = price.stream().sorted().collect(Collectors.toList());
            String Least_Price = sortedPrice.get(0);

            UpdateExcel.updateExcel(1 ,3,    Least_Price );
            UpdateExcel.updateExcel(1 ,4,"Flipkart" );
        }

        if(isAmazon)
        {
            Map<String,String> map =  AmazonHomePage.getListOfProductNames();
            List<String> price = new ArrayList<>();
            map.forEach((K,V)->{
                price.add(map.get(K));
            });
            List<String> sortedPrice = price.stream().sorted().collect(Collectors.toList());
            String Least_Price = sortedPrice.get(0);

            UpdateExcel.updateExcel(2 ,3,   Least_Price );
            UpdateExcel.updateExcel(2 ,4,"Amazon" );
        }


    }

    @And("stores the VERDICT with status as Cheapest")
    public void storesTheVERDICTWithStatusAsCheapest() {
        if(isAmazon)
        {
            String flipkartVal = UpdateExcel.ReadExcel(1,3).replaceAll(",","");
            String amazonVal = UpdateExcel.ReadExcel(2,3).replaceAll(",","");
            if(Integer.parseInt(flipkartVal) < Integer.parseInt(amazonVal))
            {
                UpdateExcel.updateExcel(1 ,5,"Cheapest" );
            }
            else if(Integer.parseInt(flipkartVal) > Integer.parseInt(amazonVal))
            {
                UpdateExcel.updateExcel(2 ,5,"Cheapest" );
            }
            else
            {
                UpdateExcel.updateExcel(1 ,5,"Both Ecomm have equally cheap price" );
            }


        }
    }
    
    @And("User closes the browser")
    public void CloseBrowser()
    {
    	baseClass.closeBrowser();
    }
    

    private void UpdateToExcel(Map<String,String> map , String Ecomm )
    {
        UpdateExcel excel = new UpdateExcel();
        int i = excel.getLastRowEnabled();
        int k = excel.getLastRowEnabled();
        if(i<0)
            i=0;

        excel.updateExcel(0 ,0,"Model Names" );
        excel.updateExcel(0 ,1,"Price" );
        excel.updateExcel(0 ,2,"Ecomm Website" );

        Set<String> set = map.keySet();
        Iterator it = set.iterator();
        while(it.hasNext())
        {
            String key = it.next().toString();
            int row = i++ + 1;
            excel.updateExcel(row,0, key );
            try {Thread.sleep(100); }catch (Exception e){}
            excel.updateExcel(row,1, map.get(key) );
            excel.updateExcel(row,2, Ecomm );
        }

    }


}
