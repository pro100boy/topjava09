package ru.javawebinar.topjava.util.exception;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String detail;

    public ErrorInfo(CharSequence url, Throwable ex) {
        //this.url = url.toString();
        //this.cause = ex.getClass().getSimpleName();
        ////while (ex.getCause() != null) ex = ex.getCause();

        //this.detail = ex.getLocalizedMessage();
        this(url, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }

    public ErrorInfo(CharSequence url, String ex, String detail) {
        this.url = url.toString();
        this.cause = ex;
        this.detail = detail;
    }
}
