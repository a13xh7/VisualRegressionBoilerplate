import org.openqa.selenium.WebElement;
import tools.DriverWrapper;
import tools.TestConfig;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.yandex.qatools.ashot.Screenshot;
import tools.DiffImageGenerator;
import tools.Screenshoter;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;

public class BaseTest
{
    protected DriverWrapper driverWrapper;
    protected WebDriver driver;
    private Screenshoter screenshoter;
    private DiffImageGenerator differ;

    @BeforeClass
    public void initBrowser()
    {
        TestConfig.initConfig();

        driverWrapper = new DriverWrapper(TestConfig.browser);
        driver = driverWrapper.getDriver();
        screenshoter = new Screenshoter(driver);
        differ = new DiffImageGenerator();
    }

    @AfterMethod
    public void removeCookies()
    {   // scroll page to the top
        driverWrapper.executeJs("window.scrollTo(0,0);");

        // remove cookies after test
        driverWrapper.executeJs("window.localStorage.clear();");
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void closeBrowser()
    {
        driver.quit();
    }

    /**
     * hide specified element before screenshot
     * only css selectors are allowed
     */
    protected void comparePageScreenshots(String fileName, String ignoredElementCssSelector)
    {
        fileName = name(fileName);

        String jsScript = String.format("document.querySelector('%s').style.visibility = 'hidden';", ignoredElementCssSelector);
        driverWrapper.executeJs(jsScript);

        screenshoter.makePageScreenshot(fileName);
        compareScreenshots(fileName);
    }

    /**
     * hide specified elements before screenshot
     * only css selectors are allowed
     */
    protected void comparePageScreenshots(String fileName, String[] ignoredElementsCssSelectors)
    {
        fileName = name(fileName);

        String jsScript;
        for (String cssSelector: ignoredElementsCssSelectors) {
            jsScript = String.format("document.querySelector('%s').style.visibility = 'hidden';", cssSelector);
            driverWrapper.executeJs(jsScript);
        }

        screenshoter.makePageScreenshot(fileName);
        compareScreenshots(fileName);
    }

    protected void comparePageScreenshots(String fileName)
    {
        fileName = name(fileName);
        screenshoter.makePageScreenshot(fileName);
        compareScreenshots(fileName);
    }

    protected void compareElementScreenshots(String fileName, String elementCssLocator)
    {
        fileName = name(fileName);
       // driverWrapper.executeJs(String.format("window.scrollTo(document.querySelector('%s').getBoundingClientRect());", elementCssLocator));
        screenshoter.makeElementScreenshot(fileName, elementCssLocator);
        compareScreenshots(fileName);
    }

    protected void compareElementScreenshots(String fileName, WebElement element)
    {
        fileName = name(fileName);
        screenshoter.makeElementScreenshot(fileName, element);
        compareScreenshots(fileName);
    }

    private void compareScreenshots(String fileName)
    {
        Screenshot actual = screenshoter.getActualScreenshot(fileName);
        Screenshot expected = screenshoter.getExpectedScreenshot(fileName);

        int diffSize = differ.getDiffSize(actual, expected);

        if(diffSize > TestConfig.allowableDiffSize)
        {
            differ.saveDiffImage(actual, expected, fileName);
            createGif(fileName);
        }

        Assert.assertTrue(diffSize <= TestConfig.allowableDiffSize, "Actual and expected screenshots are not the same. Diff size = " + diffSize);
    }

    /**
     * convert this - screenshot_name
     * to this - dev_chrome_1920x1080_screenshot_name
     */
    private String name(String pageOrElementName)
    {
        return TestConfig.env + "_" + TestConfig.browser + "_" + TestConfig.dimension + "_" + pageOrElementName;
    }

    private void createGif(String fileName)
    {
        try {

            BufferedImage first = ImageIO.read(new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS + fileName + ".png"));
            ImageOutputStream output = new FileImageOutputStream(new File(TestConfig.PATH_TO_GIF_SCREENSHOTS + fileName + ".gif"));

            tools.GifGenerator writer = new tools.GifGenerator(output, first.getType(), 500, true);
            writer.writeToSequence(first);

            File[] images = new File[]{
                    new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS + fileName + ".png"),
                    new File(TestConfig.PATH_TO_EXPECTED_SCREENSHOTS + fileName + ".png"),
                    new File(TestConfig.PATH_TO_DIFF_SCREENSHOTS + fileName + ".png"),
            };

            for (File image : images) {
                BufferedImage next = ImageIO.read(image);
                writer.writeToSequence(next);
            }

            writer.close();
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
