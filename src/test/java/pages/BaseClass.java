package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class BaseClass {
    private WebDriver driver ;


    public BaseClass() {

    }
    public BaseClass(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void launchBrowserURL(String URL , String browser)
    {

        if (browser == null || browser == "")
        {
            browser = "edge";
        }

        if (browser.equalsIgnoreCase("edge")) {
            this.driver = new EdgeDriver();
        }
        else if (browser.equalsIgnoreCase("chrome")) {
            this.driver = new ChromeDriver();
        }

        this.driver.get(URL);
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().implicitlyWait(Duration.of(10, ChronoUnit.SECONDS) );
    }

    public WebDriver getDriver()
    {
        return this.driver;
    }

    public void closeBrowser()
    {
        if(this.driver !=null)
        {
            this.driver.quit();
        }
    }
}
