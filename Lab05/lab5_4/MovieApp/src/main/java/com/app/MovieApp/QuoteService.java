package com.app.MovieApp;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    @Autowired
    private QuoteRepository repository;

    public Quote saveQuote(Quote quote) {
        return repository.save(quote);
    }

    public List<Quote> saveQuotes(List<Quote> quotes) {
        return repository.saveAll(quotes);
    }

    public List<Quote> getQuotes() {
        return repository.findAll();
    }

    public Optional<Quote> getQuoteById(Long id) {
        return repository.findById(id);
    }

    public Quote getQuoteByQuote(String quote) {
        return repository.findByQuote(quote);
    }

    public String deleteQuote(Long id) {
        repository.deleteById(id);
        return "Quote removed !! " + id;
    }

    public Quote getRandomQuote() {
        List<Quote> quotes = repository.findAll();
        Random random = new Random();
        return quotes.get(random.nextInt(quotes.size()));
    }

    public Quote getRandomQuoteByMovie(Long id) {
        List<Quote> quotes = repository.findAll();
        quotes.removeIf(quote -> quote.getMovie().getId() != id);
        Random random = new Random();
        return quotes.get(random.nextInt(quotes.size()));
    }

    public List<Quote> getLatestQuotes(int count) {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Quote::getId).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
