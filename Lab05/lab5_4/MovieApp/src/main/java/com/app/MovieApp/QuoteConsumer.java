package com.app.MovieApp;

import com.app.MovieApp.QuoteRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class QuoteConsumer {
    private final QuoteRepository quoteRepository;

    public QuoteConsumer(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @KafkaListener(topics = "lab05_quotes", groupId = "movie-app")
    public void consume(Quote quote) {
        quoteRepository.save(quote);
        System.out.println("Received quote: " + quote);
    }
    
}
