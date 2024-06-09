package com.company.automation.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;

import static com.company.automation.config.ConfigurationManager.configuration;

public enum BrowserFactory {
    CHROMIUM {
        @Override
        public Browser initialize(Playwright playwright) {

            return playwright.chromium().launch(getLaunchOptions());
        }
    },
    FIREFOX {
        @Override
        public Browser initialize(Playwright playwright) {
            return playwright.firefox().launch(getLaunchOptions());
        }
    };

    public abstract Browser initialize(Playwright playwright);

    private static ThreadLocal<Playwright> playwrightThreadLocal = ThreadLocal.withInitial(() -> Playwright.create());

    public static Playwright getPlaywright() {
        return playwrightThreadLocal.get();
    }

    private static LaunchOptions getLaunchOptions() {
        return new LaunchOptions()
                .setHeadless(configuration().headless())
                .setSlowMo(configuration().slowMotion())
                .setDownloadsPath(Paths.get("src/test/resources/Downloads"));

    }
}
