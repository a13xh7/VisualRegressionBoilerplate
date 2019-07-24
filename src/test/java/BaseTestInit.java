import tools.TestConfig;
import org.testng.annotations.Test;
import tools.Screenshoter;

import java.io.PrintWriter;

public class BaseTestInit
{
    @Test
    public void init()
    {

        TestConfig.initConfig();

        /*
         * 1. Remove actual, diff and gif screenshots
         * 2. Clear errors log
         *
         * Why we need this:
         * I am using bash script to run all tests in several resolutions(mobile, tablet, desktop).
         * BaseTestInit.init() is called before all tests, thus actual, diff and gif screenshots are removed before each resolution tests.
         * But I want to save actual screenshots for all 3 resolutions.
         * So if tests was started through bash script I don't delete screenshots here.
         * I delete screenshots and clear errors log directly in the bash script
         *
         * Actual, diff and gif screenshots will be removed if you start tests through command line
         * by default bash == 0
         */
        if(TestConfig.bash.equals("0")) {

            Screenshoter.removeActualScreenshots();
            Screenshoter.removeDiffScreenshots();
            Screenshoter.removeGifScreenshots();

            clearErrorsLog();
        }

        /*
         * Remove expected screenshots if necessary
         */
        if(TestConfig.clean.equals("1")) {
            Screenshoter.removeExpectedScreenshots();
        }
    }

    /**
     * clear errors log before all tests
     */
    private void clearErrorsLog()
    {
        try {
            PrintWriter writer = new PrintWriter(TestConfig.PATH_TO_ERRORS_LOG, "UTF-8");
            writer.println("");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
