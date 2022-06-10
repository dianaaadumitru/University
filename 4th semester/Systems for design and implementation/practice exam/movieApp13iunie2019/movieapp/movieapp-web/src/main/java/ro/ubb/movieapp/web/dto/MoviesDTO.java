package ro.ubb.movieapp.web.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoviesDTO {
    List<MovieDTO> movies;
}
