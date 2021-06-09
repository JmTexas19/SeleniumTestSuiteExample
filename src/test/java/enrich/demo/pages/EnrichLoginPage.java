package enrich.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EnrichLoginPage {
    
    //Constructor
    public EnrichLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    WebDriver driver;

    //Page Elements
    By usernameBox = By.id("username");
    By passwordBox = By.id("password");
    By loginBtn = By.id("loginButton");
    By loginPageTitle = By.xpath("//title");

    //Login using url
    public void login(String username, String password) {
        //Fill in Username and Password and click login
        driver.findElement(usernameBox).sendKeys(username);
        driver.findElement(passwordBox).sendKeys(password);
        driver.findElement(loginBtn).click();
    }

    //Get title
    public String getTitle() {
        return driver.findElement(loginPageTitle).getAttribute("textContent");
    }
}

