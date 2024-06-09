package com.company.automation.pages;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.company.automation.reporting.ExtentReport;
import com.company.automation.retry.RetryUtil;
import com.company.automation.utils.CommonUtils;
import com.company.automation.utils.LogUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import static com.company.automation.config.ConfigurationManager.configuration;

/**
 * @author Zeeshan
 */
public class BasePage {
    protected Page page;
    private static final String highlightedItem = "//li[contains(@class, 'result-selectable select2-highlighted')]";
    private static final String firstValueFromActiveDropdown = "[id='select2-drop'] >ul >li";
    private static final String valueFromActiveDropdownByName = "//div[contains(@id,'select2-drop')]//ul[@role='listbox']/li[normalize-space()='%s']";
    private static final String dropdownLocator = "//label[@title='%s']/../div/a";
    private static final String ddsSearchField = "(//label[contains(@for,'search') and contains(text(),'%s')]/following-sibling::input)[last()]";
    private static final String messageToastHeader = "div.toast-success > div";
    private static final String errorToastMessage = "div.toast-error > div";
    private static final String fieldByTagAndTextContent = "//%s[contains(text(),'%s')]";
    protected static final String DIV_CONTAINS_TEXT = "//div[contains(text(), '%s')]";
    private static final String inlineLocatorWithColumn = "//select[contains(@id, '%s-%s')]";
    private static final String firstValueFromDropdown= "//div[@class='ant-select-item-option-content']";




    private static final String popupMsg = "//div[contains(@class,'dialog-content')]//div";

    public String getPopupMessage() {
        return page.locator(popupMsg).textContent();
    }

    public Locator getDdsSearchField(String dropDownName) {
        LogUtils.info("Getting Dropdown search field Locator");
        return page.locator(String.format(ddsSearchField, dropDownName));
    }

    public Locator getFirstValueFromActiveDropdown() {
        LogUtils.info("Getting Dropdown First Value Locator");
        return page.locator(firstValueFromActiveDropdown);
    }




    public Page getPageInstance() {
        return page;
    }

    public Locator getHighlightedItem() {
        LogUtils.info("Getting Highlight Item Locator");
        return page.locator(highlightedItem);
    }

    public void setAndConfigurePage(Page page) {
        this.page = page;
        page.setDefaultTimeout(configuration().timeout());
    }

    public void captureScreenshot(String fileName) {
        page.screenshot(
                new Page.ScreenshotOptions()
                        .setPath(
                                Paths.get(configuration().baseScreenshotPath()
                                        + "/"
                                        + fileName
                                        + ".png"))
                        .setFullPage(true));
    }

    public void attachScreenshot(String description) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        String screenshotPath = System.getProperty("user.dir") + "/" + configuration().baseScreenshotPath() + "/" + timeStamp + ".png";

        // Create the directory if it does not exist
        File directory = new File(System.getProperty("user.dir") + "/" + configuration().baseScreenshotPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Path path = Paths.get(screenshotPath);
            Files.write(path, screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Attach the screenshot to the Extent report with the specified description
        ExtentReport.getTest().info(description, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }


    public void selectValueFromDropdown(String typeValue, String selectValue, String value) {
        if (CommonUtils.ifStringNotNullOrEmpty(value)) {
            if (CommonUtils.ifStringNotNullOrEmpty(value)) {
                page.click(typeValue);
                page.type(typeValue,value);
                page.waitForLoadState(LoadState.NETWORKIDLE);
                page.locator(String.format(selectValue,value)).click();
                page.waitForLoadState(LoadState.NETWORKIDLE);
                LogUtils.info("Selected " + value + " from dropdown");
            }
        }
    }

    public void selectValueFromDropdownPath(String dropdown, String value) {
        if (CommonUtils.ifStringNotNullOrEmpty(value)) {
            if (CommonUtils.ifStringNotNullOrEmpty(value)) {
                page.click(dropdown);
                page.fill(dropdown,value);
                page.waitForLoadState(LoadState.NETWORKIDLE);
                page.locator(String.format(firstValueFromDropdown,value)).click();
                page.waitForLoadState(LoadState.NETWORKIDLE);
                LogUtils.info("Selected " + value + " from dropdown");
            }
        }
    }

    public void selectValueFromMatSelectDropDown(String DropdownLocatorName, String optionValueToSelect) {
        page.locator(DropdownLocatorName).click();
        // Assuming 'mat-option' is the selector for individual options
        String optionSelector = String.format("mat-option:has-text('%s')", optionValueToSelect);
        page.waitForSelector(optionSelector);
        page.click(optionSelector);
        LogUtils.info("Selected option: " + optionValueToSelect);
    }
    public Locator getMessageToastLocator() {
        LogUtils.info("Getting Message Toast Locator");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return page.locator(messageToastHeader);
    }

    public Locator getErrorMessageToastLocator() {
        LogUtils.info("Getting Error Toast Locator");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return page.locator(errorToastMessage);
    }

    public void pressKeyboardKeys(String keyName) {
        LogUtils.info("Pressing Key : " + keyName);
        page.keyboard().press(keyName);
    }

    public void selectValueFromDropdown2(String dropDownName, String value) {
        if (CommonUtils.ifStringNotNullOrEmpty(value)) {
            RetryUtil.retryAction(() -> {
                page.locator(String.format(dropdownLocator, dropDownName)).first().click();
                page.locator(String.format(ddsSearchField, dropDownName)).first().fill(value);
                page.waitForSelector(highlightedItem);
                page.click(firstValueFromActiveDropdown);
                page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                LogUtils.info("Selected " + value + " from dropdown : " + dropDownName);
                Assert.assertTrue(page.locator(String.format(dropdownLocator, dropDownName)).first().textContent().toLowerCase().contains(value.toLowerCase()));

            }).withMessage("Retrying..!!").pollingEvery(1000).withRetryCount(3).perform();
        }
    }

    /**
     * This method will select the value from the dropdown
     * This method ensures that the value selected is exactly that needs to be selected by using the 'valueFromActiveDropdownByName' locator
     *
     * @param dropDownName dropDownName
     * @param value        value
     */
    public void selectValueFromDropdownByName(String dropDownName, String value) {
        if (CommonUtils.ifStringNotNullOrEmpty(value)) {
            RetryUtil.retryAction(() -> {
                page.locator(String.format(dropdownLocator, dropDownName)).first().click();
                page.locator(String.format(ddsSearchField, dropDownName)).first().fill(value);
                page.waitForSelector(highlightedItem);
                page.click(String.format(valueFromActiveDropdownByName, value));
                page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                LogUtils.info("Selected " + value + " from dropdown : " + dropDownName);
                Assert.assertTrue(page.locator(String.format(dropdownLocator, dropDownName)).first().textContent().toLowerCase().contains(value.toLowerCase()));

            }).withMessage("Retrying..!!").pollingEvery(1000).withRetryCount(3).perform();
        }
    }

    public void mouseMove(int xCoordinate, int yCoordinate) {
        page.mouse().move(xCoordinate, yCoordinate);
    }

    public void waitForPageLoad() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }


    public void enterValueInLocator(String locator, String value) {
        if (CommonUtils.ifStringNotNullOrEmpty(value)) {
            page.locator(locator).first().type(value);
        }
    }

    /**
     * This method will refresh the page
     */
    public void reloadPage() {
        LogUtils.info("Reloading the page.");
        page.reload(new Page.ReloadOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
    }

    /**
     * This method will set the window size
     *
     * @param width
     * @param height
     */
    public void setBrowserWidowSize(int width, int height) {
        page.setViewportSize(width, height);
    }

    /**
     * This method will return the locator based on the tag Name and the text of the element
     *
     * @param tagName tagName
     * @param text    text
     * @return Locator
     */
    public Locator getLocatorByTagAndTextContent(String tagName, String text) {
        LogUtils.info("Getting text locator By Tag : " + tagName + "And Text Content : " + text);
        return page.locator(String.format(fieldByTagAndTextContent, tagName, text));
    }

    public Locator getInlineRowLocatorWithColumn(String row, String column) {
        return page.locator(String.format(inlineLocatorWithColumn, row, column));
    }

    public String getText(String locator)
    {
        return page.locator(locator).textContent();
    }

    private static final String saveAsDraftButton = "//button[@id='saveDraft_btn_party']";
    private static final String nextButton = "//button[@id='next_btn_party']";
    private static final String titleHeading = "//h6[@class='text-primary-100 my-2 ng-star-inserted']";
    private static final String stepperNameField = "//div[normalize-space()='%s']";
    private static final String badgeField = "[id=\"badge_dropdown\"]";
    private static final String badgeValueField = "//span[contains(text(),'HMRC - HMRC')]";
    private static final String skipButton = "//span[normalize-space()='Skip']";
    private static final String confirmationPopUp = "//h2[normalize-space()='%s']";
    private static final String confirmSubmitButton = "//button[normalize-space()='Submit']";
    private static final String submitButton = "//button[@id='back_btn']";
    private static final String proceedButton = "//span[normalize-space()='Proceed']";
    private static final String heading = "//h5[@id='create_text']";
    private static final String querySuccess = "//h2[normalize-space()=\"Your Query submitted successfully\"]";
    private static final String closeButton = "//span[normalize-space()='Close']";

    public static boolean flag;
    public String generateRandomNumber() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = formatter.format(currentDate);
        Random random = new Random();
        int randomNumber = random.nextInt(99999);
        String currentDateAndRandomNumber = formattedDate + randomNumber;
        return currentDateAndRandomNumber;
    }

    public String generateRandomManifestNumber() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        String formattedDate = formatter.format(currentDate);

        String currentDateAndRandomNumber = "mawb"+ formattedDate;
        return currentDateAndRandomNumber;
    }

    public String generateTenDigitRandomNumber(){
        LocalDate today = LocalDate.now();
        String dateString = today.format(DateTimeFormatter.ofPattern("yyyyddMM"));

        Random random = new Random();
        int randomNumber1 = random.nextInt(10);
        int randomNumber2 = random.nextInt(10);

        String combinedString = dateString + randomNumber1 + randomNumber2;
        return combinedString;
    }

    public void clickSaveAsDraftButton(){
        LogUtils.info("Click Save As draft Button");
        page.locator(saveAsDraftButton).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.waitForSelector(titleHeading);
    }

    public void clickNextButton(){
        LogUtils.info("Click Next Button");
        mouseMove(0,100);
        page.locator(nextButton).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void scrollDown(){
        LogUtils.info("Scroll Down");
        mouseMove(0,100);
    }

    public void verifyStepperTitle(String stepperName){
        LogUtils.info("Verify Stepper Title");
        Assert.assertTrue(page.locator(String.format(stepperNameField,stepperName)).last().textContent().contains(stepperName));
    }

    public void clickSkipButton(){
        LogUtils.info("Click Skip Button");
        page.locator(skipButton).click();
    }

    public void clickSubmitButton(){
        LogUtils.info("Click Submit Button");
        submitButtonSummary();
        submitPopUpWindow();

    }

    public void clickNextStepper(String stepperName){
        LogUtils.info("Click Next Stepper");
        page.locator(String.format(stepperNameField,stepperName)).last().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void selectBadge(){
        page.locator(badgeField).click();
        page.locator(badgeValueField).click();
        page.locator(proceedButton).click();

    }

    public void submitPopUpWindow(){
        if(page.locator(String.format(confirmationPopUp,"Confirmation")).textContent().contains("Confirmation")){
            page.locator(confirmSubmitButton).last().click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
        }
    }

    public void submitButtonSummary(){
        page.locator(submitButton).click();
    }

    public void verifyCreateDeclarationTitle(){
        LogUtils.info("Verify Create declaration title");
        Assert.assertTrue(page.locator(heading).textContent().contains("Create Declaration"));
    }

    public boolean alertHandle(String msg){
        LogUtils.info("Query Alert Handle");
        page.waitForSelector(querySuccess);
        page.onceDialog(alert -> {
            String message = alert.message();
            if (message.contains(msg)) {
                LogUtils.info("Query Submitted Successfully");
                flag = true;
            }else {
                flag = false;
                throw new AssertionError("Query failed");
            }
            alert.dismiss();
        });
        return flag;
    }

    public void clickCloseButton(){
        page.locator(closeButton).click();
    }
    public String readJsonData(String filePath)
    {
        StringBuilder jsonData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  jsonData.toString();
    }

}