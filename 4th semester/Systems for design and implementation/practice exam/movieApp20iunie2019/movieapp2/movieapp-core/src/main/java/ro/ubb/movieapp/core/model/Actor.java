package ro.ubb.movieapp.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * author: radu
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"movie"})
@ToString(callSuper = true, exclude = {"movie"})
public class Actor extends BaseEntity<Long> {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movieId")
    private Movie movie;

    private String name;

    private int rating;
}
