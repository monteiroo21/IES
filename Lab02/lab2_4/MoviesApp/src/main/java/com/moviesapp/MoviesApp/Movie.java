package com.moviesapp.MoviesApp;

public class Movie {
    private String quote;
    private int id;
    private String movie;

    public Movie(Integer id, String movie, String quote) {
        this.id = id;
        this.movie = movie;
        this.quote = quote;
    }

    public String getQuote() {
        return this.quote;
    }

    public String getMovie() {
        return this.movie;
    }

    public Integer getId() {
        return this.id;
    }
}
