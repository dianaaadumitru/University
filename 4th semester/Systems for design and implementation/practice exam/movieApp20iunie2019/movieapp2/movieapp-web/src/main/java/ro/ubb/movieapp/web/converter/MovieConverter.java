package ro.ubb.movieapp.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.web.dto.MovieDTO;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDTO> {
    @Override
    public Movie convertDtoToModel(MovieDTO dto) {
        var model = new Movie();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setYear(dto.getYear());
        return model;
    }

    @Override
    public MovieDTO convertModelToDto(Movie movie) {
        var dto = new MovieDTO(movie.getTitle(), movie.getYear());
        dto.setId(movie.getId());
        return dto;
    }
}
