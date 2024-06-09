package com.company.automation.retry;

import com.company.automation.exceptions.ActionFailedException;
import com.company.automation.client.BaseClient;
import com.company.automation.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

public class ActionRetry extends RetryUtil {

    private final Action action;
    private int pollingTime;

    public ActionRetry(final Action action) {
        this.action = action;
    }

    public ActionRetry ignoringError() {
        this.ignoreError = true;
        return this;
    }

    public ActionRetry withRetryCount(final int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public ActionRetry withMessage(final String message) {
        this.failureMessage = message;
        return this;
    }

    public ActionRetry pollingEvery(final int milliSeconds) {
        this.pollingTime = milliSeconds;
        return this;
    }

    public ActionRetry withBackOffAction(final Action backOffAction) {
        this.backOffAction = backOffAction;
        return this;
    }

    public void perform() {
        Throwable lastException = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                action.perform();
                return;
            } catch (Throwable t) {
                lastException = t;
                LogUtils.info(String.format("Retrying action Again due to error %s", t.getMessage()));
                BaseClient.waitByTimeUnit(pollingTime, TimeUnit.MILLISECONDS);
                backOff();
            }
        }
        if (StringUtils.isEmpty(failureMessage)) {
            throw new ActionFailedException(String.format("Action failed after retrying %s times",
                    retryCount), lastException);
        }
        throw new ActionFailedException(failureMessage);
    }
}
