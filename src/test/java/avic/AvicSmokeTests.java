package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;


import static org.testng.Assert.*;

public class AvicSmokeTests {

    private WebDriver driver;

    @BeforeTest
    public void profileSetUp(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }

    @BeforeMethod
    public void testSetUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/ua");
    }

    @Test(priority = 1)
    public void checkCorrectPrice(){
        driver.findElement(By.xpath("//div[contains(text(),'899 грн')]")).click();
        driver.findElement(
                By.xpath("//a[contains(@data-ecomm-cart,'Power Bank IRONN Magnetic Wireless 10000mAh Black')]")).click();
        String expectedPrice = "899 грн";
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                ("//div[@class='item-total']//span[@class='prise']")));
        String actualPrice = driver.findElement(By.xpath("//div[@class='item-total']//span[@class='prise']")).getText();
        assertEquals(expectedPrice,actualPrice);
    }

    @Test(priority = 2)
    public void changeOfProductColor(){
        driver.get("https://avic.ua/ua/macbook");
        driver.findElement(By.xpath("//img[contains(@title,' Silver 2020')]")).click();
        driver.findElement(By.xpath("//div[@class='choose-config flex-wrap']//a[contains(@href,'gold')]")).click();
        String ExpectedMessage = "Gold";
        String ActualMessage = driver.findElement(By.xpath("//h1[contains(text(),'Gold')]")).getText();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Gold')]")).getText().contains("Gold"));
    }

    @Test(priority = 3)
    public void correctIphoneSortingDesc(){
        driver.findElement(By.xpath("//div[@class='top-links__box']//a[contains(@href,'iphone-12')]")).click();
        WebElement sort = driver.findElement(By.xpath("//*[@id='js_category_filter']/div/div[2]/div/div[1]/div[2]/span"));
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOf(sort));
        sort.click();
        driver.findElement(By.xpath("//li[text()='Від дорогих до дешевих']")).click();
        List<WebElement> iphoneDescList = driver.findElements(By.xpath("//div[@class='prod-cart__prise-new']"));
        String first = iphoneDescList.get(0).getText();
        String last = iphoneDescList.get(11).getText();
        String first2 = first.substring(0,5);
        String last2 = last.substring(0,5);

        int max = Integer.parseInt(first2);
        int min = Integer.parseInt(last2);
        for (WebElement iphone:iphoneDescList) {
            String s = iphone.getText();
            String s2 = s.substring(0,5);
            int i = Integer.parseInt(s2);
            assertTrue(max>=i);
            assertTrue(min<=i);
        }
    }
}
