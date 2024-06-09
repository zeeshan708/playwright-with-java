package com.company.automation.e2e;

import com.company.automation.browser.BrowserManager;
import com.company.automation.client.login.LoginClient;
import com.company.automation.listeners.TestListener;
import com.company.automation.pages.login.LoginPage;
import com.company.automation.pages.BasePage;
import com.company.automation.pages.BasePageFactory;
import com.company.automation.utils.LogUtils;
import com.microsoft.playwright.*;
import org.testng.annotations.*;

import java.awt.*;


@Listeners(TestListener.class)
public class BaseTest {

    private static final String backConfirmPopup ="//h1[normalize-space()=\"Please Confirm\"]";
    private static final String okButton ="//span[normalize-space()='Ok']";
    private static final String createDeclarationBUtton ="//span[normalize-space()='Create Declaration']";
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    public LoginPage loginPage;
    public LoginClient loginClient;
    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    public void initialization() {
        loginPage = createInstance(LoginPage.class);
        loginClient = new LoginClient(loginPage);

    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        browser = BrowserManager.browser();
        playwright = BrowserManager.getPlaywright();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = browserContext.newPage();
        initialization();
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        browserContext.close();
        BrowserManager.removeBrowser();
        BrowserManager.removePlaywright();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
            LogUtils.info("AfterSuite Method");
        }

    @BeforeMethod(alwaysRun = true)
    public  void beforeMethod(){
    }

    @AfterMethod(alwaysRun = true)
    public  void AfterMethod(){

    }
    public  void doLogin(String username, String password){
            System.out.println(username + " " + password);
            loginClient.LoginFunctionality(username, password);
            loginPage.verifyLogin();
            loginPage.attachScreenshot("user is logged in to the system.");
    }

}



