package com.app.MovieApp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MovieController {
    @Autowired
    MovieService movieService;
    QuoteService quoteService;

    @GetMapping("/movies")
    public List<Movie> showMovieList() {
        return movieService.getMovies();
    }

    @GetMapping("/quotes")
    public List<Quote> showQuoteList() {
        return quoteService.getQuotes();
    }
    
    @PostMapping("/addmovie")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @PostMapping("/addquote")
    public Quote addQuote(@RequestBody Quote quote) {
        return quoteService.saveQuote(quote);
    }

    @PostMapping("/addmovies")
    public List<Movie> addMovies(@RequestBody List<Movie> movies) {
        return movieService.saveMovies(movies);
    }

    @PostMapping("/addquotes")
    public List<Quote> addQuotes(@RequestBody List<Quote> quotes) {
        return quoteService.saveQuotes(quotes);
    }

    @GetMapping("/movies/{id}")
    public Optional<Movie> findMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/quotes/{id}")
    public Optional<Quote> findQuoteById(@PathVariable Long id) {
        return quoteService.getQuoteById(id);
    }

    @GetMapping("/movies/{title}")
    public Movie findMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    @GetMapping("/quotes/{quote}")
    public Quote findQuoteByQuote(@PathVariable String quote) {
        return quoteService.getQuoteByQuote(quote);
    }
        
    @DeleteMapping("/movies/{id}")
    public String deleteMovie(@PathVariable Long id) {
        return movieService.deleteMovie(id);
    }

    @DeleteMapping("/quotes/{id}")
    public String deleteQuote(@PathVariable Long id) {
        return quoteService.deleteQuote(id);
    }

    @GetMapping("/quote")
    public Quote getRandomQuote() {
        return quoteService.getRandomQuote();
    }

    @GetMapping("/quote/{id}")
    public Quote getRandomQuote(@PathVariable Long id) {
        return quoteService.getRandomQuoteByMovie(id);
    }
}
