package tools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.*;


import java.util.concurrent.TimeUnit;

public class DriverWrapper
{
    private WebDriver driver;
    private JavascriptExecutor js;

    public DriverWrapper(String browser)
    {
        /*
         * 4 browsers. you can add new browsers here
         *
         * 1 - chrome
         * 2 - chrome headless
         * 3 - firefox
         * 4 - firefox headless
         */
        switch(browser)
        {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "ch":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "fh":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(true);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                WebDriverManager.chromedriver().setup();
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
                driver = new ChromeDriver();
        }

        // SET IMPLICITLY WAIT
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // CREATE JS EXECUTOR
        js = (JavascriptExecutor)driver;

        // SET BROWSER WINDOW SIZE
        if(TestConfig.dimension == null) {
            driver.manage().window().maximize();
        } else {
            String[] parts = TestConfig.dimension.split("x");
            String width = parts[0];
            String height = parts[1];
            Dimension dimension = new Dimension(Integer.parseInt(width), Integer.parseInt(height));
            driver.manage().window().setSize(dimension);
        }
    }

    public WebDriver getDriver()
    {
        return driver;
    }

    public void wait(int seconds)
    {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void executeJs(String script)
    {
        try {
            js.executeScript(script);
        } catch (Exception e) {}
    }

    public void waitForVisible(String cssSelector)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.cssSelector(cssSelector)) ));
        } catch (Exception e) {

        }
    }

    public void waitForUrlContains(String urlChunk)
    {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlContains(urlChunk));
    }

    /**
     * Scroll page using vanilla javascript
     */
    protected void vanillaScrollBottomTop()
    {
        executeJs("window.scrollTo(0,document.body.scrollHeight);");
        executeJs("window.scrollTo(0,0);");
    }

    /**
     * Prepare page before taking screenshot.
     * Scroll page to bottom and back to top to ensure that all elements and images were loaded
     */
    public void preparePageForScreenshot()
    {
        vanillaScrollBottomTop();
        vanillaScrollBottomTop();
        wait(1);
        vanillaScrollBottomTop();
        vanillaScrollBottomTop();
    }
}
