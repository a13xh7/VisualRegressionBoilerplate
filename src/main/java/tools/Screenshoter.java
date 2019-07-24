package tools;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Screenshoter
{
    private Screenshot actualScreenshot;
    private Screenshot expectedScreenshot;

    private File actualFile;
    private File expectedFile;

    private WebDriver driver;

    public Screenshoter(WebDriver driver)
    {
        this.driver = driver;
    }

    public void makePageScreenshot(String fileName)
    {
        // make page screenshot
        this.actualScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
        this.actualFile = new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS + fileName + ".png");

        // save page screenshot
        try {
            ImageIO.write(this.actualScreenshot.getImage(), "png", this.actualFile);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        this.saveActualScreenshotAsExpectedIfExpectedDoesNotExist(fileName);
    }

    public void makeElementScreenshot(String fileName, String elementCssLocator)
    {
        // make element screenshot
        WebElement myWebElement = driver.findElement(By.cssSelector(elementCssLocator));

        this.actualScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(driver, myWebElement);

        this.actualFile = new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS + fileName + ".png");

        // save element screenshot
        try {
            ImageIO.write(this.actualScreenshot.getImage(), "png", this.actualFile);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        this.saveActualScreenshotAsExpectedIfExpectedDoesNotExist(fileName);
    }

    public void makeElementScreenshot(String fileName, WebElement element)
    {
        // make element screenshot
        this.actualScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(driver, element);

        this.actualFile = new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS + fileName + ".png");

        // save element screenshot
        try {
            ImageIO.write(this.actualScreenshot.getImage(), "png", this.actualFile);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        this.saveActualScreenshotAsExpectedIfExpectedDoesNotExist(fileName);
    }

    private void saveActualScreenshotAsExpectedIfExpectedDoesNotExist(String fileName)
    {
        this.expectedFile = new File(TestConfig.PATH_TO_EXPECTED_SCREENSHOTS + fileName + ".png");

        if(!expectedFile.exists()) {
            this.expectedScreenshot = this.actualScreenshot;
            try {
                ImageIO.write(this.actualScreenshot.getImage(), "png", this.expectedFile);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.expectedScreenshot = new Screenshot(ImageIO.read(expectedFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Screenshot getActualScreenshot(String fileName)
    {
        return this.actualScreenshot;
    }

    public Screenshot getExpectedScreenshot(String fileName)
    {
        return this.expectedScreenshot;
    }

    public static void removeExpectedScreenshots()
    {
        File directory = new File(TestConfig.PATH_TO_EXPECTED_SCREENSHOTS);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeActualScreenshots()
    {
        File directory = new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeDiffScreenshots()
    {
        File directory = new File(TestConfig.PATH_TO_DIFF_SCREENSHOTS);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeGifScreenshots()
    {
        File directory = new File(TestConfig.PATH_TO_GIF_SCREENSHOTS);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
