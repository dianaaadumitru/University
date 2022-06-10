package ro.ubb.movieapp.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ActorDTO extends BaseDto{
    private Long id;
    private String name;
    private int rating;
    private Long movieId;
}