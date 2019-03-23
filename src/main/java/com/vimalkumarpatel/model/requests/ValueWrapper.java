package com.vimalkumarpatel.model.requests;

import com.vimalkumarpatel.model.User;

public class ValueWrapper {


    private User user;

    private SummaryRequest summaryRequest;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public SummaryRequest getSummaryRequest() {
        return summaryRequest;
    }

    public void setSummaryRequest(SummaryRequest summaryRequest) {
        this.summaryRequest = summaryRequest;
    }
}
