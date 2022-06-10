package ro.ubb.movieapp.web.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MovieWithActorsDTO extends BaseDto{
    private Long id;
    private String title;
    private int year;
    private List<ActorDTO> actors;
}
