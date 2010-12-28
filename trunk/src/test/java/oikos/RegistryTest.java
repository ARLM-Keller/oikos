package oikos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RegistryTest
{

    public static void main(String[] args)
    {
        WebDriver driver = new FirefoxDriver();

        driver.get("http://localhost:8080/user/Index");

        driver.findElement(By.id("person.name")).sendKeys("Leandro");
        driver.findElement(By.id("person.email")).sendKeys("leandro@email.com");
        driver.findElement(By.id("person.password")).sendKeys("pwd");
        
        driver.findElement(By.id("church.name")).sendKeys("IBN Boas Novas");
        driver.findElement(By.id("church.membersCount")).sendKeys("100");
        driver.findElement(By.id("church.comments")).sendKeys("Aleluia!");
        
        driver.findElement(By.id("sendForm")).submit();

        System.out.println("Page title is: " + driver.getTitle());
    }
}
