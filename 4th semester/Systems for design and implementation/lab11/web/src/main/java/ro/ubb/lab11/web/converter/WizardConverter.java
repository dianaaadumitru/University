package ro.ubb.lab11.web.converter;


import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Wizard;
import ro.ubb.lab11.web.dto.WizardDTO;

@Component
public class WizardConverter extends BaseConverter<Long, Wizard, WizardDTO> {
    @Override
    public Wizard convertDtoToModel(WizardDTO dto) {
        var model = new Wizard();
        model.setId(dto.getId());
        model.setAge(dto.getAge());
        model.setName(dto.getName());
        model.setPet(dto.getPet());

        return model;
    }

    @Override
    public WizardDTO convertModelToDto(Wizard wizard) {
        var dto = new WizardDTO(wizard.getName(), wizard.getAge(), wizard.getPet());
        dto.setId(wizard.getId());
        return dto;
    }
}
