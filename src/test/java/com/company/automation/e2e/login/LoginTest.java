package com.company.automation.e2e.login;

import com.company.automation.data.login.LoginData;
import com.company.automation.e2e.BaseTest;
import com.epam.reportportal.annotations.TestCaseId;
import com.epam.reportportal.annotations.attribute.Attribute;
import com.epam.reportportal.annotations.attribute.Attributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.company.automation.config.ConfigurationManager.configuration;
import static com.company.automation.utils.JsonDataProviderUtils.processJson;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.AssertJUnit.assertTrue;


/**
 * @author Zeeshan Asghar
 */
@Attributes(attributes = {
        @Attribute(key = "Author", value = "Zeeshan Asghar"),
        @Attribute(key = "Module", value = "Login")
})
public class LoginTest extends BaseTest {
    private static final String LOGIN_FILE = "login/login.json";

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData(Method testMethod) throws JsonProcessingException {
        String testCaseId = testMethod.getAnnotation(Test.class).testName();
        return processJson(
                LoginData.class, LOGIN_FILE, testCaseId);
    }

    @Test(
            testName = "Login-1",
            dataProvider = "loginData",
            priority = 1,
            groups = {"smoke", "regression"})
    @TestCaseId("Login-1")
    @Attributes(attributes = {@Attribute(key = "Author", value = "Zeeshan Asghar")})
    public void testLogin(LoginData loginDto) {

        loginClient.LoginFunctionality(configuration().adminUsername(), configuration().adminPassword());
        loginPage.verifyLogin();
        loginPage.attachScreenshot("user is logged in");
    }
}

