package com.company.automation.utils;


import com.aventstack.extentreports.Status;
import com.company.automation.reporting.ExtentReport;
import com.google.common.io.BaseEncoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author Aniket
 */
public class LogUtils {
    //Initialize Log4j2 instance
    private static final Logger LOGGER = LogManager.getLogger(LogUtils.class);
    private static final Logger RPLOGGER = LogManager.getLogger("ReportPortal Logger");

    //Info Level Logs
    public static void pass(String message) {
        LOGGER.info(message);
        ExtentReport.getTest().log(Status.PASS, message);

    }

    public static void info(String message) {
        if(ExtentReport.getTest()!=null){
            ExtentReport.getTest().log(Status.INFO, message);
            LOGGER.info(message);
        }
    }

    public static void info(Object message) {
        LOGGER.info(message);
    }


    public static void info(String message, Throwable throwable) {
        LOGGER.info(message);
    }

    //Warn Level Logs
    public static void warn(String message) {
        ExtentReport.getTest().log(Status.WARNING, message);
        LOGGER.warn(message);
    }

    public static void skip(String message) {
        ExtentReport.getTest().log(Status.SKIP, message);
        LOGGER.warn(message);
    }

    public static void warn(Object message) {
        LOGGER.warn(message);
    }

    //Error Level Logs
    public static void error(String message) {
        ExtentReport.getTest().log(Status.FAIL, message);
        LOGGER.error(message);
    }

    public static void error(Object message) {
        ExtentReport.getTest().log(Status.FAIL, (String) message);
        LOGGER.error(message);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error(message);
    }

    public static void fail(String message) {
        ExtentReport.getTest().log(Status.FAIL, message);
        LOGGER.error(message);
    }


    public static void error(Object message, Throwable throwable) {
        LOGGER.error(message);
    }

    //Fatal Level Logs
    public static void fatal(String message) {
        LOGGER.fatal(message);
    }

    public static void fatal(Object message) {
        LOGGER.fatal(message);
    }

    //Debug Level Logs
    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void debug(Object message) {
        LOGGER.debug(message);
    }

    public static void log(File file, String message) {
        LOGGER.info("RP_MESSAGE#FILE#{}#{}", file.getAbsolutePath(), message);
    }

    public static void logByte(byte[] bytes, String message) {
        LOGGER.info("RP_MESSAGE#BASE64#{}#{}", BaseEncoding.base64().encode(bytes), message);
    }

    public static void logBase64(String base64, String message) {
        LOGGER.info("RP_MESSAGE#BASE64#{}#{}", base64, message);
    }

    public static void success(String message) {
        ExtentReport.getTest().log(Status.INFO, message);
        LOGGER.info("[SUCCESS] : " + message);
    }
}
