package app;

import tools.DriverWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import tools.TestConfig;


public class BasePage
{
    /**
     * base url must be without slash at the end
     * page urls must start with slash - /page
     * or vice versa
     */
    protected String baseUrl;
    protected String pageUrl;

    protected DriverWrapper driverWrapper;
    protected WebDriver driver;

    public BasePage(DriverWrapper driverWrapper, String pageUrl)
    {
        setBaseUrl();
        this.pageUrl = pageUrl;

        this.driverWrapper = driverWrapper;
        this.driver = driverWrapper.getDriver();

        PageFactory.initElements(driver, this);
    }

    /**
     * Set base url
     * You can set different urls for different environments (dev, stage, production)
     */
    public void setBaseUrl()
    {
        switch(TestConfig.env)
        {
            case "dev":
                baseUrl = "https://laravel.com";
                break;
            case "stage":
                baseUrl = "https://laravel.com";
                break;
            case "prod":
                baseUrl = "https://laravel.com";
                break;
            default:
                baseUrl = "https://laravel.com";
        }
    }

    public WebDriver getDriver()
    {
        return driver;
    }

    public DriverWrapper getDriverWrapper()
    {
        return driverWrapper;
    }

    public void open()
    {
        driver.get(baseUrl + pageUrl);
    }

    public void openPage(String page)
    {
        driver.get(baseUrl + page);
    }

    public void refresh()
    {
        driver.navigate().refresh();
    }

    public void wait(int seconds)
    {
        driverWrapper.wait(seconds);
    }

    /**
     * Prepare page before taking screenshot.
     * 1. Remove unwanted elements.
     * 2. Scroll page to bottom and back to top to ensure that all elements and images were loaded
     */
    public void preparePageForScreenshot()
    {
        driverWrapper.preparePageForScreenshot();
    }


}
