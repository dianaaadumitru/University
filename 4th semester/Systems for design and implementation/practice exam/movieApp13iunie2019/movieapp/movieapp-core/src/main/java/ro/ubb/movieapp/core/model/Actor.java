package ro.ubb.movieapp.core.model;

import lombok.*;

import javax.persistence.Entity;

/**
 * author: radu
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class Actor extends BaseEntity<Long> {
    private String name;

    private int rating;

    private Long movieId;


}
