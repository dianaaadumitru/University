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
@ToString(callSuper = true, exclude = {"actors"})
@NamedEntityGraphs({
        @NamedEntityGraph(name="movieWithActors",
                attributeNodes = @NamedAttributeNode(value="actors"))
})
public class Movie extends BaseEntity<Long> {
    private String title;

    private int year;

    //actors
    @OneToMany(mappedBy = "movie", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Actor> actors;


}
