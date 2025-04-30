package swapnilcodes;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) {

        //StandAloneTest for Adding Product

        // With Webdrivermanager chromedriver will be downloaded according to our version number
        // No need to download separate chromedriver
        // Setup chromedriver with help of Webdrivermanager dependency
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Applying implicit wait at global level
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Login Scenario:
        driver.get("https://rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys("swapatel@abc.com");
        driver.findElement(By.id("userPassword")).sendKeys("Abc@123456789");
        driver.findElement(By.id("login")).click();

        // Adding Product Scenario:
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        // Using Java Streams to filter products
        // Otherway is to use For Loop for it
       WebElement prod =  products.stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);

       // For click on Add to Cart have to find button element to click
        // reducing scope of findElement by using cssSelector
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();




    }
}
