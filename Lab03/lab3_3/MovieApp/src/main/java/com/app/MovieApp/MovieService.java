package com.app.MovieApp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    public Movie saveMovie(Movie movie) {
        return repository.save(movie);
    }

    public List<Movie> saveMovies(List<Movie> movies) {
        return repository.saveAll(movies);
    }

    public List<Movie> getMovies() {
        return repository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return repository.findById(id);
    }

    public Movie getMovieByTitle(String title) {
        return repository.findByTitle(title);
    }

    public String deleteMovie(Long id) {
        repository.deleteById(id);
        return "Movie removed !! " + id;
    }
}
