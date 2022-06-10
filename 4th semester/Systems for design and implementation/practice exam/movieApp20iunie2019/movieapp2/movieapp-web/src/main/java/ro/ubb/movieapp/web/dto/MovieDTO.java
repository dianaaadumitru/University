package ro.ubb.movieapp.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MovieDTO extends BaseDto{
    private String title;
    private int year;
}
