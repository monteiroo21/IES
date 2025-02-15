package com.app.MovieApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findById(long id);
    Movie findByTitle(String title);
    void deleteById(long id);
}
