package com.exercise.model;

public enum SearchType {

    BY_CUSTOMER("CUST"),
    BY_STATUS("STATUS"),
    BY_AGENT("AGENT");

    private SearchType(String search) {
        this.search = search;
    }
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
