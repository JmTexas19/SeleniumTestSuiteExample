package enrich.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EnrichHomePage {

    //Constructor
    public EnrichHomePage(WebDriver driver) {
        this.driver = driver;
    }

    WebDriver driver;

    //Page Elements
    By homePageTitle = By.xpath("//title");
    By projectTabBtn = By.xpath("//div[@class=\"mainTab\"]//a[.=\"Projects\"]");
    
    //Get title
    public String getTitle() {
        return driver.findElement(homePageTitle).getAttribute("textContent");
    }

    //Click Projects Tab Btn
    public void clickProjectsTab() {
        driver.findElement(projectTabBtn).click();
    }
}
