package ro.ubb.movieapp.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.web.dto.ActorDTO;
import ro.ubb.movieapp.web.dto.MovieDTO;
import ro.ubb.movieapp.web.dto.MovieWithActorsDTO;

import java.util.stream.Collectors;

@Component
public class MovieWithActorConverter extends BaseConverter<Movie, MovieWithActorsDTO> {
    @Override
    public Movie convertDtoToModel(MovieWithActorsDTO dto) {
        var model = new Movie();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setYear(dto.getYear());
        return model;
    }

    @Override
    public MovieWithActorsDTO convertModelToDto(Movie movie) {
        var dto = new MovieWithActorsDTO(movie.getId(), movie.getTitle(), movie.getYear(), movie.getActors().stream().map(
                actor -> new ActorDTO(actor.getId(), actor.getName(), actor.getRating(), actor.getMovie().getId())
        ).collect(Collectors.toList()));
        dto.setId(movie.getId());
        return dto;
    }
}
