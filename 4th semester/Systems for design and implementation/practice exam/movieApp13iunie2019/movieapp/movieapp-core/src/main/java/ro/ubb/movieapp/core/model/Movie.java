package ro.ubb.movieapp.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * author: radu
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@NamedEntityGraphs({
        @NamedEntityGraph(name="movieWithActors",
                attributeNodes = @NamedAttributeNode(value="actors"))
})
public class Movie extends BaseEntity<Long> {
    private String title;

    private int year;

    //actors
    @OneToMany(fetch = FetchType.LAZY)
    private List<Actor> actors;
}
