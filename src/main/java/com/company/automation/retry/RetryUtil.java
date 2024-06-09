package com.company.automation.retry;

public class RetryUtil {

    protected int retryCount = 3;
    protected String failureMessage = "";
    protected boolean ignoreError;
    protected Action backOffAction = new DefaultAction();

    public static ActionRetry retryAction(final Action action) {
        return new ActionRetry(action);
    }

    protected void backOff() {
        try {
            backOffAction.perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
