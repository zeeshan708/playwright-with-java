package com.company.automation.utils;


import com.company.automation.data.login.LoginData;
import com.company.automation.endpoints.ApiEndpoints;
import com.company.automation.helpers.AuthenticationHelper;
import com.google.common.collect.Maps;
import com.microsoft.playwright.APIResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.util.Strings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.company.automation.config.ConfigurationManager.configuration;

public class CommonUtils {

    public static boolean ifStringNotNullOrEmpty(String s) {
        return !Strings.isNullOrEmpty(s);
    }


    public static JSONObject readTenantSettingsFromRevertFilePath(File file) throws IOException {

        String jsonContent = Files.readString(file.toPath());
        JSONObject jsonObject = new JSONObject(jsonContent);
        return jsonObject;
    }

    public static boolean verifyDateFormat(String dateFormatPattern, String date) {
        boolean result = true;
        DateFormat dateFormatter = new SimpleDateFormat(dateFormatPattern);
        try {
            dateFormatter.parse(date.replaceAll(",", ""));
        } catch (Exception e) {
            result = false;
        }
        LogUtils.info("Expected Date Format: " + dateFormatPattern);
        LogUtils.info("Actual Date: " + date);
        return result;
    }

    public static boolean verifyDateFormatReport(String dateFormatPattern, String dateString) {
        LogUtils.info("Expected Date Format: " + dateFormatPattern);
        LogUtils.info("Actual Date: " + dateString);
        DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }



    public static String getRandomAlphanumeric(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }

    public static String getRandomNumeric(int size) {
        return RandomStringUtils.randomNumeric(size);
    }

    public static String processDate(String dateToken) {
        String[] tokens = dateToken.substring(1, dateToken.length() - 1).split(";");

        Map<String, String> calendarMap = Maps.newHashMap();

        for (String token : tokens) {
            String[] tokenValues = token.split(":");
            String key = tokenValues[0];
            String value = tokenValues[1];
            calendarMap.put(key, value);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(calendarMap.get("format"));

        localDateTime = localDateTime.plusDays(Integer.parseInt(calendarMap.get("day")));
        localDateTime = localDateTime.plusMonths(Integer.parseInt(calendarMap.get("month")));
        localDateTime = localDateTime.plusYears(Integer.parseInt(calendarMap.get("year")));

        return dateTimeFormatter.format(localDateTime);
    }

    public static APIResponse performQuery(LoginData loginData, String query) {
        HashMap<Object, Object> body = Maps.newHashMap();
        body.put("Query", query);
        return new APIUtil().sendAPIRequest(configuration().baseUrl(), ApiEndpoints.DUMMY, "POST", AuthenticationHelper.getAuthHeaders(loginData), body);
    }

    /**
     * This method will return the date in the specified format w.r.t to the current date
     * <br> Enter positive integers for future dates </br>
     * <br> Enter negative integers for past dates </br>
     * @param dateFormat dateFormat
     * @param days days
     * @return
     */
    public static String getDate(String dateFormat, int days) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, days);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        return simpleDateFormat.format(calendar.getTime());
    }

    public static String dateGenerationWithoutTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = new Date();
        String currentDateWithoutTime = sdf.format(currentDate);
        return currentDateWithoutTime;
    }

    public static String dateGenerationWithTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date currentDate = new Date();
        String currentDateWithTime = sdf.format(currentDate);
        return currentDateWithTime;
    }

}
