package ro.ubb.movieapp.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.core.service.MovieService;
import ro.ubb.movieapp.web.converter.MovieConverter;
import ro.ubb.movieapp.web.converter.MovieWithActorConverter;
import ro.ubb.movieapp.web.dto.MovieDTO;
import ro.ubb.movieapp.web.dto.MovieWithActorsDTO;
import ro.ubb.movieapp.web.dto.MoviesDTO;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private MovieWithActorConverter movieWithActorConverter;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MoviesDTO getMovies() {
        List<Movie> movies = movieService.getAllMovies();
        MoviesDTO dtos = new MoviesDTO(movieConverter.convertModelsToDtos(movies));
        return dtos;
    }

    @RequestMapping(value = "/movieWithActors/{movieId}", method = RequestMethod.GET)
    MovieWithActorsDTO getMovieWithActors(@PathVariable Long movieId) {
        Movie movie = movieService.getMovieWithActors(movieId);
        MovieWithActorsDTO dto = movieWithActorConverter.convertModelToDto(movie);
        return dto;
    }

    @RequestMapping(value = "addActor/{movieId}/{actorId}", method = RequestMethod.GET)
    MovieWithActorsDTO addActor(@PathVariable Long movieId, @PathVariable Long actorId) {
        var result = movieService.addActor(movieId, actorId);
        var resultModel = movieWithActorConverter.convertModelToDto(result);
        return resultModel;
    }
}
