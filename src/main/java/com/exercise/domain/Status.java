package com.exercise.domain;

public enum Status {
    OPEN("open"),
    WAITING_FOR_CUSTOMER("waiting for customer"),
    CUSTOMER_RESPONDED("customer responded"),
    RESOLVED("resolved"),
    CLOSED("closed");


    private String status;

    private Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
