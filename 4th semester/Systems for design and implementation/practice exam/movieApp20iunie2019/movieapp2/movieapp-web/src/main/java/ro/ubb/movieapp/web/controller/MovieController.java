package ro.ubb.movieapp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.core.service.MovieService;
import ro.ubb.movieapp.web.converter.MovieConverter;
import ro.ubb.movieapp.web.converter.MovieWithActorConverter;
import ro.ubb.movieapp.web.dto.MovieWithActorsDTO;
import ro.ubb.movieapp.web.dto.MoviesDTO;
import ro.ubb.movieapp.web.dto.MoviesWithActorsDTO;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private MovieWithActorConverter movieWithActorConverter;

    @RequestMapping(value="/movies/{year}/{lessThan}", method = RequestMethod.GET)
    MoviesDTO getMovies(@PathVariable int year, @PathVariable boolean lessThan) {
        List<Movie> movies = movieService.getMoviesByYear(year, lessThan);
        MoviesDTO dtos = new MoviesDTO(movieConverter.convertModelsToDtos(movies));
        return dtos;
    }

    @RequestMapping(value="/moviesWithActors/{year}/{lessThan}", method = RequestMethod.GET)
    MoviesWithActorsDTO getMoviesWithActors(@PathVariable int year, @PathVariable boolean lessThan) {
        List<Movie> movies = movieService.getMoviesWithActorsByYear(year, lessThan);
        MoviesWithActorsDTO dtos = new MoviesWithActorsDTO(movieWithActorConverter.convertModelsToDtos(movies));
        return dtos;
    }

    @RequestMapping(value = "/delete/{movieID}/{actorID}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteActor(@PathVariable Long movieID, @PathVariable Long actorID) {
        movieService.deleteActor(movieID, actorID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
