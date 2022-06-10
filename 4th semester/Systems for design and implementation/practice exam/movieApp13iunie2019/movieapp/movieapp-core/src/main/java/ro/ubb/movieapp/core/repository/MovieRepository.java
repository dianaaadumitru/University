package ro.ubb.movieapp.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.movieapp.core.model.Movie;

import java.util.List;

/**
 * author: radu
 */
public interface MovieRepository extends MovieAppRepository<Movie, Long> {
//    @Query("select m from Movie m where m.id=:movieID")
//    @EntityGraph(value="movieWithActors", type =
//            EntityGraph.EntityGraphType.LOAD)
//    Movie findMovieWithActors(@Param("movieID") Long movieID);
    Movie findMovieByIdBetween(Long movieId);
}
