package ro.ubb.lab11.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.CastedSpell;
import ro.ubb.lab11.web.dto.CastedSpellDTO;

@Component
public class CastedSpellConverter extends BaseConverter<Long, CastedSpell, CastedSpellDTO> {
    @Override
    public CastedSpell convertDtoToModel(CastedSpellDTO dto) {
        var model = new CastedSpell();
        model.setId(dto.getId());
        model.setWizardId(dto.getWizardId());
        model.setSpellId(dto.getSpellId());
        model.setDetails(dto.getDetails());
        return model;
    }

    @Override
    public CastedSpellDTO convertModelToDto(CastedSpell castedSpell) {
        var ticketDTO = new CastedSpellDTO(castedSpell.getWizardId(), castedSpell.getSpellId(), castedSpell.getDetails());
        ticketDTO.setId(castedSpell.getId());
        return ticketDTO;
    }
}
