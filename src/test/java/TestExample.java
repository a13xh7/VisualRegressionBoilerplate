import app.App;
import app.IndexPage;
import org.testng.annotations.Test;

public class TestExample extends BaseTest
{
    @Test
    public void hardcoreExample()
    {
        //it's not necessary to use App class or page objects
        driver.get("http://laravel.com");
        driverWrapper.preparePageForScreenshot();
        comparePageScreenshots("index_page_hardcode");
    }

    @Test
    public void indexPage()
    {
        // you can create app object in BaseTest class to avoid code duplication
        App app = new App(driverWrapper, "/");

        app.indexPage.open();
        app.preparePageForScreenshot();
        comparePageScreenshots("index_page");
    }

    @Test
    public void indexPageElement()
    {
        App app = new App(driverWrapper, "/");

        app.indexPage.open();
        app.preparePageForScreenshot();

        // You can use css selectors
        compareElementScreenshots("index_page_element", IndexPage.FORGE_BLOCK);

        // Or find and pass webElement
        //compareElementScreenshots("index_page_element", driver.findElement(By.cssSelector("a.full.forge")));
    }

    @Test
    public void ignorePageElementsExample()
    {
        App app = new App(driverWrapper, "/");

        app.indexPage.open();
        app.preparePageForScreenshot();

        // You can ignore one element
        comparePageScreenshots("index_page_ignored_element", IndexPage.MACKBOOK_BLOCK);

         // Or many elements
        //comparePageScreenshots("index_page_ignored_element", new String[]{"section.panel.features.dark", "div.macbook"});
    }
}
