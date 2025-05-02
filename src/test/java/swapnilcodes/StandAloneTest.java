package swapnilcodes;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) {

        String productName = "ZARA COAT 3";

        //StandAloneTest for Adding Product

        // With Webdrivermanager chromedriver will be downloaded according to our version number
        // No need to download separate chromedriver
        // Setup chromedriver with help of Webdrivermanager dependency
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        // Applying implicit wait at global level
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Login Scenario:
        driver.get("https://rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys("swapatel@abc.com");
        driver.findElement(By.id("userPassword")).sendKeys("Abc@123456789");
        driver.findElement(By.id("login")).click();

        // Applying Explicit Wait at Global Level
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Adding Product Scenario:
        // wait until products are visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        // Using Java Streams to filter products
        // Otherway is to use For Loop for it
       WebElement prod =  products.stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);

        // For click on Add to Cart have to find button element to click
        // reducing scope of findElement by using cssSelector
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        // Product is added message disappears in seconds so we have to capture when it appears and disappears
        // we are using explicit wait for this element
        // Wait untill visible of message element
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

        // also capturing invisibility of this loading element
        // ng-animating
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

        // Click on Cart button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@routerlink='/dashboard/cart']")));
        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

        // Cart Page - Total Items capturing
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

        // Using Java Streams to filter products
       Boolean match =  cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
        // If Product Names are matching as per selected it returns true from above method
        Assert.assertTrue(match);

        // Checkout Button click
        driver.findElement(By.cssSelector(".totalRow button")).click();

        // For Checkout Page
        // Using Actions Class to interact with Country Autodropdown
        Actions countryTextbox = new Actions(driver);
        countryTextbox.sendKeys(driver.findElement((By.cssSelector(("[placeholder='Select Country']")))), "india").build().perform();

        // wait until country dropdown is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector((".ta-results"))));

        // Click on India from dropdown
        driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();

        // Click on Place Order button
        driver.findElement(By.cssSelector(".action__submit")).click();

        // Thank you Page
       String confirmedMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();

       // Selenium compares text with on UI not in HTML DOME
       Assert.assertTrue(confirmedMessage.equalsIgnoreCase("Thankyou for the order."));


       //Close the browser
         driver.close();






    }
}
