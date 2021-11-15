package main;

import org.testng.annotations.*;
import utils.CommonUtils;
import java.io.IOException;

public class BaseTest extends CommonUtils {

    @BeforeSuite
    public void loader() throws IOException {
        initialSetup();
    }

    @BeforeTest
    public void openBrowser() {
        openChromeBrowser();
    }

    @AfterClass
    public static void endTest()
    {
       endExtentTest();
    }

    @AfterSuite
    public void closeConnection()
    {
        closeExtentReport();
        closeBrowser();
    }
}