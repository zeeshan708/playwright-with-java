package com.company.automation.exceptions;

/**
 * Is used to be thrown in runtime in cases when a block of elements or a page object
 * can't be instantiated or initialized.
 *
 * @author Zeeshan
 * Date: 16.09.2023
 */
public class ActionFailedException extends RuntimeException {
    public ActionFailedException() {
        super();
    }

    public ActionFailedException(String message) {
        super(message);
    }

    public ActionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionFailedException(Throwable cause) {
        super(cause);
    }
}
