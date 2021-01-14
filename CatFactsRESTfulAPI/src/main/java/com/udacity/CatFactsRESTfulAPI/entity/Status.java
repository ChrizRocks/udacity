package com.udacity.CatFactsRESTfulAPI.entity;

public class Status {
    private boolean verified;
    private int sentCount;

    public Status(boolean verified, int sentCount) {
        this.verified = verified;
        this.sentCount = sentCount;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    //"status":{"verified":true,"sentCount":1}
}
