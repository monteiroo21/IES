package com.moviesapp.MoviesApp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MoviesController {
    List<Movie> movieList;

    public MoviesController() {
        movieList = new ArrayList<>();
        movieList.add(new Movie(1, "Casablanca (1942)", "This is the beginning of a beautiful friendship"));
        movieList.add(new Movie(2, "Creed 2 (2018)", "If you want to change things in a big way, then you gotta make some big changes."));
        movieList.add(new Movie(3, "Shawshank Redemption (1994)", "Some birds aren't meant to be caged. Their feathers are just too bright."));
        movieList.add(new Movie(4, "Jobs (2013)", "The people who are crazy enough to think that they can change the world, are the one's who do"));
        movieList.add(new Movie(5, "Interstellar (2014)", "This world’s a treasure, but it’s been telling us to leave for a while now."));
        movieList.add(new Movie(6, "Casablanca (1942)", "Play it, Sam. Play 'As Time Goes By."));
        movieList.add(new Movie(7, "The Lion King (1994)", "The past can hurt. But the way I see it, you can either run from it or learn from it."));
        movieList.add(new Movie(8, "Joker (2019)", "Is it just me, or is it getting crazier out there?"));
        movieList.add(new Movie(9, "The Dark Knight (2008)", "Sometimes the truth isn’t good enough, sometimes people deserve more. Sometimes people deserve to have their faith rewarded"));
        movieList.add(new Movie(10, "Creed 2 (2018)", "If we didn’t do what we loved, we wouldn’t exist."));
    }

    @GetMapping("/quote")
	public String quote(Model model) {
		int idx = (int) (Math.random() * movieList.size());
        Movie movie = movieList.get(idx-1);
        model.addAttribute("movie", movie.getMovie());
        model.addAttribute("quote", movie.getQuote());
        return "quote";
	}

    @GetMapping("/shows")
	public String shows(Model model) {
        List<String> movies = new ArrayList<>();
        for (Movie movie: movieList) {
            movies.add(movie.getMovie());
        }
		model.addAttribute("movies", movies);
        return "movies";
	}

    @GetMapping("/quotes")
	public String quotes(@RequestParam(name="id", required=true) Integer id, Model model) {
		for (Movie movie: movieList) {
            if (movie.getId() == id) {
                model.addAttribute("movie", movie.getMovie());
                model.addAttribute("quote", movie.getQuote());
                break;
            }
        }
        return "quote";
	}
}
