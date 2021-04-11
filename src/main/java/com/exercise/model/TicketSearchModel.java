package com.exercise.model;

import com.exercise.domain.Ticket;

public class TicketSearchModel {
    private SearchType searchType;
    private String searchValue;


    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}
