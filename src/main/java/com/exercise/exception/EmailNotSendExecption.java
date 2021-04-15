package com.exercise.exception;

public class EmailNotSendExecption extends Exception {
    private String exception;

    public EmailNotSendExecption(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "EmailNotSendExecption{" +
                "exception='" + exception + '\'' +
                '}';
    }
}
