package com.company.automation.pages;

import com.microsoft.playwright.Page;

/**
 * @author Zeeshan
 */
public final class BasePageFactory {
    protected BasePageFactory() {}

    public static <T extends BasePage> T createInstance(Page page, Class<T> basePage) {
        try {
            BasePage instance = basePage.getDeclaredConstructor().newInstance();
            instance.setAndConfigurePage(page);

            return basePage.cast(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Page class instantiation failed.");
    }
}
