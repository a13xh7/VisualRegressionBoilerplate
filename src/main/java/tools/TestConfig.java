package tools;

public class TestConfig
{
    /*
     * All path are relative
     */
    public static final String PATH_TO_ACTUAL_SCREENSHOTS = "screenshots/actual/";
    public static final String PATH_TO_EXPECTED_SCREENSHOTS = "screenshots/expected/";
    public static final String PATH_TO_DIFF_SCREENSHOTS = "screenshots/diff/";
    public static final String PATH_TO_GIF_SCREENSHOTS = "screenshots/gifs/";

    public static final String PATH_TO_REPORT_TEMPLATE = "report/report-template.html";
    public static final String PATH_TO_OUTPUT_REPORT_FILE = "report/REPORT.html";
    public static final String PATH_TO_ERRORS_LOG = "report/errors.log";

    /**
     *  Test environment and browser settings
     *  All settings must be static
     */
    public static String browser = "chrome";
    public static String env = "dev";
    public static String clean = "0";
    public static String dimension = "1920x1080";
    public static String bash = "0";

    /*
     * Allowable difference size (number of different pixels) between two screenshots
     */
    public static int allowableDiffSize = 10;

    /**
     * Init config. Set default settings.
     * Default setting will be set if you are using IDE to run tests or if you didn't specify some settings
     * this method MUST be called before everything else(before tests, before creating tools objects, before creating page objects)
     */
    public static void initConfig()
    {
        browser = System.getProperty("browser");
        if(browser == null) {
            browser = "chrome";
        }

        env = System.getProperty("env");
        if(env == null) {
            env = "dev";
        }

        clean = System.getProperty("clean");
        if(clean == null) {
            clean = "0";
        }

        bash = System.getProperty("bash");
        if(bash == null) {
            bash = "0";
        }

        dimension = System.getProperty("dimension");
        if(dimension == null) {
            dimension = "1920x1080";
        }
    }

}
