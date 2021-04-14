package com.exercise.domain;

public class ResponseError {

    private String errorMessage;
    private int respStatus = 1;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(int respStatus) {
        this.respStatus = respStatus;
    }
}
