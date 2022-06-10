package ro.ubb.movieapp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ro.ubb.movieapp.core.model.Actor;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.core.repository.MovieRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author: radu
 */

@org.springframework.stereotype.Service
@Transactional
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getMoviesByYear(int year, boolean lessThan) {
//        List<Movie> result;
//        if (lessThan) {
//            result = movieRepository.findAllByYearLessThanEqualOrderByYearDesc(year);
//        } else {
//            result = movieRepository.findAllByYearGreaterThanEqualOrderByYearDesc(year);
//        }
//        return result;
        return movieRepository.findAll(Sort.by("year").descending())
                .stream()
                .filter(movie -> {
                    if (lessThan)
                        return movie.getYear() <= year;
                    return movie.getYear() >= year;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> getMoviesWithActorsByYear(int year, boolean lessThan) {
        List<Movie> result;
        if (lessThan) {
            result = movieRepository.findAllWithActorsLess(year);
        } else {
            result = movieRepository.findAllWithActorsGreater(year);
        }
        return result;
//        throw new RuntimeException("not yet implemented");
    }

    @Override
    @Transactional
    public void deleteActor(Long movieId, Long actorId) {
        Optional<Movie> movie = movieRepository.findByIdQuery(movieId);
        movie.ifPresent(elem->{
            Actor actor=elem.getActors().stream().filter(filtering->filtering.getId()== actorId).findFirst().get();
            elem.getActors().remove(actor);
        });

//        throw new RuntimeException("not yet implemented");
    }
}
