package enrich.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EnrichProjectsPage {
    //Constructor
    public EnrichProjectsPage(WebDriver driver) {
        this.driver = driver;
    }

    WebDriver driver;

    //Page Elements
    By projectsPageTitle = By.xpath("//title");
    By projectAvniman = By.xpath("//*[@class=\"g-value\" and .=\"Avniman\"]");

    //Get title
    public String getTitle() {
        return driver.findElement(projectsPageTitle).getAttribute("textContent");
    }

    //Click Project Avniman
    public void clickProjectAvniman() {
        driver.findElement(projectAvniman).click();
    }
}
