package com.company.automation.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

import static com.company.automation.config.ConfigurationManager.configuration;

/**
 * This class manages the browser instance used in tests.
 */
public final class BrowserManager {
    private static ThreadLocal<Playwright> playwrightThreadLocal = ThreadLocal.withInitial(Playwright::create);
    private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();

    private BrowserManager() {}

    public static Browser browser() {
        Browser browser = browserThreadLocal.get();
        if (browser == null) {
            Playwright playwright = playwrightThreadLocal.get();
            browser = BrowserFactory.valueOf(configuration().browser().toUpperCase()).initialize(playwright);
            browserThreadLocal.set(browser);
        }
        return browser;
    }

    public static void removeBrowser() {
        Browser browser = browserThreadLocal.get();
        if (browser != null) {
            browser.close();
            browserThreadLocal.remove();
        }
    }

    public static Playwright getPlaywright(){
        return playwrightThreadLocal.get();
    }

    public static void removePlaywright() {
        Playwright playwright = playwrightThreadLocal.get();
        if (playwright != null) {
            playwright.close();
            playwrightThreadLocal.remove();
        }
    }
}