package com.app.MovieApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Quote findById(long id);
    Quote findByQuote(String quote);
    void deleteById(long id);
}
