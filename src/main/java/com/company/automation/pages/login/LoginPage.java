package com.company.automation.pages.login;

import com.company.automation.pages.BasePage;
import com.company.automation.utils.LogUtils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.testng.Assert;

import static com.company.automation.config.ConfigurationManager.configuration;
import static org.testng.AssertJUnit.assertTrue;

/**
 * This class captures the relevant UI components and functionalities of the login page.
 *
 * @author Zeeshan
 */
public class LoginPage extends BasePage {
    private static final String emailIdText = "//input[@id='username']";
    private static final String passwordText = "//input[@id='password']";
    private static final String loginButton = "//button[@id='submit']";
    private static final String loginError = "#errordiv>div[class='kc-feedback-text']";
    private static final String userProfileIcon = "//*[text()='N' or text()='U' or role='presentation'][1]";
    private static final String dashboardActionMenu = "//a[@class='tooltip-bottom']";
    private static final String logoutButton = "//a[@id='stickymenulogout']//span";

    private static final String cargoesIcon = "//a[@id='applicationUrl']//img";
    private static final String welcomeText = "//h5[normalize-space()='Welcome to Cargoes Community']";




    public LoginPage goTo() {
        LogUtils.info("Navigating to Base URL: " + configuration().baseUrl());
        try {
            page.navigate(configuration().baseUrl());
            LogUtils.info("Redirecting to Login URL: " + page.url());
            waitForPageLoad();
            // assertTrue(page.url().contains("staging-login.cargoes.com"));
        } catch (Exception e) {
            LogUtils.error("Navigation to Base URL timed out, trying again...");
            page.reload();
            page.navigate(configuration().baseUrl(), new Page.NavigateOptions().setTimeout(10000));
        }
        return this;
    }

    public LoginPage enterUsername(String username) {
        LogUtils.info("Enter the UserName: " + username);
        page.type(emailIdText, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        LogUtils.info("Enter the Password: " + password);
        page.type(passwordText, password);
        return this;
    }

    public void clickLogin() {
        LogUtils.info("Click on Login Button");
        page.click(loginButton);
    }

    public void verifyLogin() {
        LogUtils.info("URL redirected to Base URL"+configuration().baseUrl());
        System.out.println(page.url());
        String environment = System.getProperty("env");
        LogUtils.info("Environment: "+environment);
        assertTrue("User profile icon is displayed", isUserProfileIconVisible());
    }

    public Boolean isUserProfileIconVisible() {
        LogUtils.info("Check if User Profile Icon is visible");
        waitForPageLoad();
        return page.url().contains("logged-in-successfully");
    }

    public void performLogout()
    {
        LogUtils.info("perform logout");
        //page.reload();
        waitForPageLoad();
        page.waitForTimeout(10000);
        page.waitForSelector(dashboardActionMenu);
        page.click(dashboardActionMenu);
        page.waitForSelector(logoutButton);
        page.locator(logoutButton).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

    }
    public Boolean isCargoesIconVisible() {
        LogUtils.info("Check if cargoes  Icon is visible");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return page.isVisible(cargoesIcon);
    }
    public void verifyLogoutAction() {
        LogUtils.info("Check if user has logged out successfully");
        Assert.assertTrue(page.locator(welcomeText).textContent().contains("Welcome to Cargoes Community"));

    }


}
