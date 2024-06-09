package com.company.automation.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryAnalyzer implements IRetryAnalyzer, IAnnotationTransformer {
    private int count = 0;
    private static final int MAX_RETRY_COUNT = 1; // set the maximum number of retries here

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRY_COUNT) {
            // if the test has failed, retry it
            if (result.getStatus() == ITestResult.FAILURE) {
                count++;
                return true;
            }
        }
        return false;
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}