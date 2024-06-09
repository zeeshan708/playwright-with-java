package com.company.automation.client;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.microsoft.playwright.options.WaitForSelectorState.ATTACHED;

/**
 * @author Zeeshan
 */
public class BaseClient {
    private static SecureRandom random= new SecureRandom();


    public int getRandomNumber() {
        int randomNumber= Math.abs(ThreadLocalRandom.current().nextInt());
        return randomNumber;
    }

    public static String generateRandomString(int length) {

        String text ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        for( int i = 0; i < length; i++ )
            sb.append( text.charAt( random.nextInt(text.length()) ) );
        return sb.toString();

    }

    public static String generateRandomEmail() {

        String text ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(8);
        for( int i = 0; i < 8; i++ )
            sb.append( text.charAt( random.nextInt(text.length())));

        sb.append("@gmail.com");
        return sb.toString().toLowerCase(Locale.ROOT);

    }

    public static String formatDate(LocalDate date, String dateFormat) {
        // Format the date in the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return date.format(formatter);
    }

    public static String generateRandomNumericString(int length) {
        String textnumber ="123456789";
        StringBuilder sb = new StringBuilder(length);
        for( int i = 0; i < length; i++ )
            sb.append( textnumber.charAt( random.nextInt(textnumber.length()) ) );
        return sb.toString();

    }

    public static void selectValueFromDropdown(Locator locator, String value){
        locator.click();
        locator.type(value);
        waitByTimeUnit(2,TimeUnit.SECONDS);
        locator.press("Enter");
    }

    public static void clearAllFilters(List<ElementHandle> locator){
        for (ElementHandle element : locator) {
            element.click();
        }

    }

    public static void waitByTimeUnit(final int time, final TimeUnit timeUnit) {
        try {
            int sleepTime = time;
            if (timeUnit.equals(TimeUnit.SECONDS)) {
                sleepTime = time * 1000;
            } else if (timeUnit.equals(TimeUnit.MILLISECONDS)) {
                sleepTime = time;
            }
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
