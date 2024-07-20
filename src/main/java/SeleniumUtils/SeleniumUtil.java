package SeleniumUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class SeleniumUtil {
    public static void enterText(WebDriver driver , WebElement ele, String text)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(ele));
        ele.sendKeys(text);
    }

    public static void verifyBrokenLink(List<WebElement> links , String EcomWebsite) throws Exception {

        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url == null || url.isEmpty()) {
                System.out.println(EcomWebsite + " - URL is either not configured for anchor tag or it is empty");
                continue;
            }
            try {
                HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
                huc.setRequestMethod("HEAD");
                int respCode = huc.getResponseCode();
                if (respCode >= 400) {
                    System.out.println(url + " is a broken link in - " + EcomWebsite+ " with Status Code - " + respCode);
                } else {
//                    System.out.println(url + " is a valid link in  - " + EcomWebsite);
                }
            } catch (MalformedURLException e) {
                System.out.println(EcomWebsite + " - Malformed URL: " + url);
            } catch (IOException e) {
                System.out.println(EcomWebsite + " - Error connecting to URL: " + url);
            }
        }
    }

}
