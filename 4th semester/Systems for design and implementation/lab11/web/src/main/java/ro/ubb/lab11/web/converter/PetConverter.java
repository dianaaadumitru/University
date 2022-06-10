package ro.ubb.lab11.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Pet;
import ro.ubb.lab11.web.dto.PetDTO;

@Component
public class PetConverter extends BaseConverter<Long, Pet, PetDTO> {
    @Override
    public Pet convertDtoToModel(PetDTO dto) {
        var model = new Pet();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setWid(dto.getWid());
        model.setBreed(dto.getBreed());
        return model;
    }

    @Override
    public PetDTO convertModelToDto(Pet pet) {
        var dto = new PetDTO(pet.getName(), pet.getBreed(), pet.getWid());
        dto.setId(pet.getId());
        return dto;
    }
}
