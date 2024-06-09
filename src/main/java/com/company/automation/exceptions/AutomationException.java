package com.company.automation.exceptions;

public class AutomationException extends RuntimeException {
    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(String message, Exception e) {
        super(message, e);
    }

    public AutomationException(Throwable cause) {
        super(cause);
    }
}
