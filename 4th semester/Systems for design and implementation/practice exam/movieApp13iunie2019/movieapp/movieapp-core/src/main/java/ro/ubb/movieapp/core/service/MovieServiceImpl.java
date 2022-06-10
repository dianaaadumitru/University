package ro.ubb.movieapp.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.movieapp.core.model.Actor;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.core.repository.ActorRepository;
import ro.ubb.movieapp.core.repository.MovieRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * author: radu
 */

@org.springframework.stereotype.Service
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;


    @Override
    public List<Movie> getAllMovies() {
//        return movieRepository.findAll().stream().filter(
//                movie -> {
//                    return movie.getTitle().contains()
//                }
//        );
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieWithActors(Long movieId) {
        return movieRepository.findMovieWithActors(movieId);
    }

    @Override
    @Transactional
    public Movie addActor(Long movieId, Long actorId) {
        Actor actor = actorRepository.getOne(actorId);
        actor.setMovieId(movieId);
        Movie movie = movieRepository.getOne(movieId);
        movie.getActors().add(actor);
        return movie;
    }
}
