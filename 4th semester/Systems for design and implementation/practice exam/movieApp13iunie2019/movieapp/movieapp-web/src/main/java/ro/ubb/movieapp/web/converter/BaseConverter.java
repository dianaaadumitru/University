package ro.ubb.movieapp.web.converter;

import ro.ubb.movieapp.core.model.BaseEntity;
import ro.ubb.movieapp.web.dto.BaseDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by radu.
 */

public abstract class BaseConverter<Model extends BaseEntity<Long>, Dto extends BaseDto> implements
        Converter<Model, Dto> {

    public List<Long> convertModelsToIDs(Collection<Model> models) {
        return models.stream().map(model -> model.getId()).collect(Collectors.toList());
    }

    public List<Long> convertDTOsToIDs(Collection<Dto> dtos) {
        return dtos.stream()
                .map(dto -> dto.getId())
                .collect(Collectors.toList());
    }

    public List<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(model -> convertModelToDto(model))
                .collect(Collectors.toList());
    }
}
