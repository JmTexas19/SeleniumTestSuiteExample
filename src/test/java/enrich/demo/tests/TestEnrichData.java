package enrich.demo.tests;

//JUnit Testing
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import java.util.Random;

//Logging
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.TimeUnit;

//Selenium Libraries
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.By;

//Webpages
import enrich.demo.pages.EnrichLoginPage;
import enrich.demo.pages.EnrichProjectAvnimanPage;
import enrich.demo.pages.EnrichProjectsPage;
import enrich.demo.pages.EnrichHomePage;

//Libraries for extracting URL
import org.apache.http.client.utils.URLEncodedUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import java.util.Collections;
import java.util.List;

//Libraries and Classes for getting data from endpoint
import com.google.gson.Gson;
import enrich.demo.RawFactResponse;


public class TestEnrichData {
    
    WebDriver driver;
    LogEntries entries;
    private final static Logger LOGGER = Logger.getLogger(TestEnrichData.class.getName());

    //Pages
    EnrichLoginPage enrichLoginPage;
    EnrichHomePage enrichHomePage;
    EnrichProjectsPage enrichProjectsPage;
    EnrichProjectAvnimanPage enrichProjectAvnimanPage;

    //Login Information
    String url = "https://pharmatest.enrichconsulting.com/";
    String qaUsername = "jmarin";
    String qaPassword = "PingPongtabletennis21!";
    int annualSales1_1 = new Random().nextInt(1000);

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);

        LOGGER.setLevel(Level.INFO);
    }

    /* Test Case Details:
    * ID:               TC1
    * Priority:         High
    * Title:            Test Sales_volume Input Data
    * Description:      Tests data integrity of inputting sales into Sales_volume
    * Preconditions:    None
    */
    @RepeatedTest(1)
    public void TestSalesVolumeInput() {
        //Initialize Pages
        enrichLoginPage = new EnrichLoginPage(driver);
        enrichHomePage = new EnrichHomePage(driver);
        enrichProjectsPage = new EnrichProjectsPage(driver);
        enrichProjectAvnimanPage = new EnrichProjectAvnimanPage(driver);

        //Login
        printJSErrors();
        LOGGER.log(Level.INFO, "Logging into the system...");
        assertEquals("Login to the Enrich Analytics Platform", enrichLoginPage.getTitle());
        enrichLoginPage.login(qaUsername, qaPassword);
        printJSErrors();
        assertEquals("Enrich Analytics Platform Homepage", enrichHomePage.getTitle());
        LOGGER.log(Level.INFO, "OPENED: Enrich Analytics Platform Homepage");
        
        //Navigate to Projects
        LOGGER.log(Level.INFO, "Navigating to Projects Tab...");
        enrichHomePage.clickProjectsTab();
        printJSErrors();
        assertEquals("Authorized Projects", enrichProjectsPage.getTitle());
        LOGGER.log(Level.INFO, "OPENED: Authorized Projects Page");

        //Select Project Avniman
        LOGGER.log(Level.INFO, "Selecting project Avniman...");
        enrichProjectsPage.clickProjectAvniman();
        printJSErrors();
        assertEquals("Avniman", enrichProjectAvnimanPage.getTitle());
        LOGGER.log(Level.INFO, "OPENED: Avniman Page");

        //Get parameters from URL
        String projId = "";
        String tempId = "";
        List<NameValuePair> params = getURLParams(driver.getCurrentUrl());
        for (NameValuePair param : params) {
            if(param.getName().equals("projid")) {
                projId = param.getValue();
            }
            if(param.getName().equals("tempid")) {
                tempId = param.getValue();
            }
        }
        
        //Assert extracted parameters are not empty
        assertNotEquals("", projId);
        assertNotEquals("", tempId);
        LOGGER.log(Level.INFO, "projId: " + projId + " | tempId: " + tempId);

        //Call endpoint and get data
        String rawFactDataEndpoint = String.format
            ("%s/FactTableService/rawfact/proj/%s/%s/Revenue_yrs_base/", url, projId, tempId);
        LOGGER.log(Level.INFO, "GET: " + rawFactDataEndpoint);
        assertEquals("Revenue_yrs_base", getRawFactData(rawFactDataEndpoint).name);
        LOGGER.log(Level.INFO, "OPENED: Revenue_yrs_base.json");

        //Navigate to Input > Sales
        LOGGER.log(Level.INFO, "Navigating to Input>Sales for Project...");
        driver.get(String.format("%s/ProjSelect.aspx?openProject=yes&projid=%s&tempid=%s", url, projId, tempId));
        assertEquals("Avniman", enrichProjectAvnimanPage.getTitle());
        LOGGER.log(Level.INFO, "OPENED: Avniman Page");

        //Select Annual Forecast
        LOGGER.log(Level.INFO, "Selecting Annual Forecast for Annual Sales...");
        enrichProjectAvnimanPage.clickSales();
        enrichProjectAvnimanPage.selectAnnualForecastMethod();
        assertEquals("Annual Forecast", enrichProjectAvnimanPage.getCurrentSalesMethod());
        LOGGER.log(Level.INFO, "OPENED: Annual Forecast for Annual Sales");

        //Set Annual Forecast
        LOGGER.log(Level.INFO, "Entering Value for Annual Sales 1_1...");
        enrichProjectAvnimanPage.setAnnualSalesVolume1_1(annualSales1_1);
        assertEquals(annualSales1_1, enrichProjectAvnimanPage.getAnnualSalesVolume1_1());
        LOGGER.log(Level.INFO, String.format("SET Value %d for Annual Sales 1_1", annualSales1_1));

        //Validate change in endpoint
        LOGGER.log(Level.INFO, "Validating data integrity of Annual Sales 1_1...");
        assertEquals(annualSales1_1 * 1000000, Integer.parseInt(getRawFactData(rawFactDataEndpoint).data.get(0)));
    }

    //Get RawFactData
    public RawFactResponse getRawFactData(String endpoint) {
        driver.get(endpoint);
        By jsonData = By.tagName("pre");
        return new Gson().fromJson(driver.findElement(jsonData).getText(), RawFactResponse.class);
    }

    //Get JS Errors
    public void printJSErrors() {
        LogEntries entries = driver.manage().logs().get(LogType.BROWSER);
        for(LogEntry entry : entries) {
            if(entry.getLevel() == Level.SEVERE) {
                LOGGER.log(Level.SEVERE, "[JSERROR] " + entry.getMessage());
            }
        }
    }

    //Get parameters from given url
    public List<NameValuePair> getURLParams(String url) {
        try {
            return URLEncodedUtils.parse(new URI(url), Charset.forName("UTF-8"));
        }
        catch (URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        //Return empty list if failed
        return Collections.emptyList();
    }

    @AfterEach
    public void teardown() {
        LOGGER.info("Shutting Down...");
        driver.quit();
    }
}