package ro.ubb.lab11.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Spell;
import ro.ubb.lab11.web.dto.SpellDTO;

@Component
public class SpellConverter extends BaseConverter<Long, Spell, SpellDTO>{
    @Override
    public Spell convertDtoToModel(SpellDTO dto) {
        var model = new Spell();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        return model;
    }

    @Override
    public SpellDTO convertModelToDto(Spell spell) {
        var dto = new SpellDTO(spell.getName(), spell.getDescription());
        dto.setId(spell.getId());
        return dto;
    }
}
