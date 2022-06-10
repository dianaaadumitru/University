package ro.ubb.movieapp.core.service;

import ro.ubb.movieapp.core.model.Movie;

import java.util.List;

/**
 * author: radu
 */
public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieWithActors(Long movieId);
    Movie addActor(Long movieId, Long actorId);

}
