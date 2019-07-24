package app;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import tools.DriverWrapper;

public class IndexPage extends BasePage
{
    /*
     * Site elements for which I'm creating and comparing screenshots
     */
    public static final String FORGE_BLOCK = "a.full.forge";
    public static final String MACKBOOK_BLOCK = "div.macbook";

    /*
     * WebElement example
     */
    @FindBy(css="#search-input")
    private WebElement searchField;

    public IndexPage(DriverWrapper driverWrapper, String pageUrl) {
        super(driverWrapper, pageUrl);
    }

}
