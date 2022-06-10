package ro.ubb.movieapp.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movieapp.core.model.Actor;
import ro.ubb.movieapp.web.dto.ActorDTO;

@Component
public class ActorConverter extends BaseConverter<Actor, ActorDTO> {
    @Override
    public Actor convertDtoToModel(ActorDTO dto) {
        var model = new Actor();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setRating(dto.getRating());
        model.setMovieId(dto.getMovieId());
        return model;
    }

    @Override
    public ActorDTO convertModelToDto(Actor actor) {
        var dto = new ActorDTO(actor.getId(), actor.getName(), actor.getRating(), actor.getMovieId());
        return dto;
    }
}
