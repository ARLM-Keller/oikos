package oikos;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumTestSupport
{
    protected static Selenium browser;
    
    protected static final String TIME_OUT = "5000";
    
    @AfterClass
    public static void tearDown()
    {
        if(browser != null)
        {
            browser.stop();
        }
    }

    @BeforeClass
    public static void setUp()
        throws Exception
    {
        browser = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8887/");
        browser.setSpeed("125");
        browser.start();
    }

    protected void waitPage()
    {
        browser.waitForPageToLoad(TIME_OUT);
    }

}

