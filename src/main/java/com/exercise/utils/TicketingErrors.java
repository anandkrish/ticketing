package com.exercise.utils;

public enum TicketingErrors {

    TICKET_NOT_FOUND(100,"Ticket not Found"),
    USER_NOT_FOUND(201, "User not found");

    private TicketingErrors(int errorNumber, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorNumber = errorNumber;
    }

    @Override
    public String toString() {
        return "TicketingErrors{" +
                "errorNumber=" + errorNumber +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    private TicketingErrors() {

    }
   private int errorNumber;
    private String errorMessage;

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
