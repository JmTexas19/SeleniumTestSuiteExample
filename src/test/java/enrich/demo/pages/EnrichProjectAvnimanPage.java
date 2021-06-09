package enrich.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class EnrichProjectAvnimanPage {
    //Constructor
    public EnrichProjectAvnimanPage(WebDriver driver) {
        this.driver = driver;
    }

    WebDriver driver;

    //Page Elements
    By projectPageTitle = By.xpath("//title");
    By projectInputsBtn = By.xpath("//b[.=\"Inputs\"]");
    By projectSalesBtn = By.xpath("//a[.=\"Sales\"]");
    By salesEntryDropdown = By.xpath("//*[@id=\"919517743_Market_method_chosen\"]");
    By salesEntryDropdownCurrent = By.xpath("//*[@id=\"919517743_Market_method_chosen\"]/a/span");
    By salesEntryDropdownAnnualForecast = By.xpath("//*[contains(@class,\"active-result\") and .=\"Annual Forecast\"]");
    By salesEntryDropdownPriceVolume = By.xpath("//*[contains(@class,\"active-result\") and .=\"Price and Volume\"]");
    By annualSales1_1 = By.xpath("//*[@var_name=\"Annual_sales\"]//td[1]");

    //Get title
    public String getTitle() {
        return driver.findElement(projectPageTitle).getAttribute("textContent");
    }

    //Expand Inputs
    public void expandInputs() {
        driver.switchTo().frame("list");
        driver.findElement(projectInputsBtn).click();
        driver.switchTo().defaultContent();
    }

    //Click Sales
    public void clickSales() {
        if(driver.findElements(projectSalesBtn).size() > 0) {
            driver.switchTo().frame("list");
            driver.findElement(projectSalesBtn).click();
            driver.switchTo().defaultContent();
        }
        else {
            expandInputs();
            driver.switchTo().frame("list");
            driver.findElement(projectSalesBtn).click();
            driver.switchTo().defaultContent();
        }
    }

    //Get Current Sales Method
    public String getCurrentSalesMethod() {
        driver.switchTo().frame("detail");
        String salesMethod = driver.findElement(salesEntryDropdownCurrent).getText();
        driver.switchTo().defaultContent();
        return salesMethod;        
    }

    //Select Annual Forecast Method
    public void selectAnnualForecastMethod() {
        driver.switchTo().frame("detail");
        driver.findElement(salesEntryDropdown).click();
        driver.findElement(salesEntryDropdownAnnualForecast).click();
        driver.switchTo().defaultContent();
    }

    //Select Price and Volume Method
    public void selectPriceVolumeMethod() {
        driver.switchTo().frame("detail");
        driver.findElement(salesEntryDropdown).click();
        driver.findElement(salesEntryDropdownPriceVolume).click();
        driver.switchTo().defaultContent();
    }

    //Enter value for Sales Volume 1
    public void setAnnualSalesVolume1_1(int val) {
        driver.switchTo().frame("detail");
        driver.findElement(annualSales1_1).click();
        new Actions(driver).sendKeys(String.valueOf(val)).perform();
        new Actions(driver).sendKeys(Keys.ENTER).perform();
        driver.switchTo().defaultContent();
    }

    //Get value for Sales Volume 1
    public int getAnnualSalesVolume1_1() {
        driver.switchTo().frame("detail");
        String val = driver.findElement(annualSales1_1).getText();
        driver.switchTo().defaultContent();
        return Integer.parseInt(val);
    }

}
