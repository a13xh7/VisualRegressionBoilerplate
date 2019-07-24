package app;

import tools.DriverWrapper;

public class App extends BasePage
{
    public IndexPage indexPage;

    public App(DriverWrapper driverWrapper, String pageUrl)
    {
        super(driverWrapper, pageUrl);

        indexPage = new IndexPage(driverWrapper, "/");
    }
}
