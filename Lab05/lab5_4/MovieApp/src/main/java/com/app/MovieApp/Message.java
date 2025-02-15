package com.app.MovieApp;

import java.util.List;

public class Message {
    private List<Quote> quotes;

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return "Message [" +
               "quotes=" + quotes +
               ']';
    }
}
