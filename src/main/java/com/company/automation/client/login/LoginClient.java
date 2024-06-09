package com.company.automation.client.login;

import com.company.automation.client.BaseClient;
import com.company.automation.pages.login.LoginPage;
import com.company.automation.utils.LogUtils;

/**
 * @author Zeeshan
 */
public class LoginClient extends BaseClient {

    private LoginPage loginPage;

    public LoginClient(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void LoginFunctionality(String username, String password) {
        loginPage
                .goTo()
                .enterUsername(username)
                .enterPassword(password)
                .clickLogin();
        loginPage.attachScreenshot("user is logged in");
        LogUtils.info("User is performing login Action");
    }
    public void performAndVerifyLogout()
    {
        loginPage.performLogout();
        loginPage.verifyLogoutAction();
        LogUtils.info("User is logged out successfully");
    }

}
