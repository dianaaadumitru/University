package ro.ubb.lab11.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.lab11.core.model.CastedSpell;
import ro.ubb.lab11.core.service.ICastedSpellService;
import ro.ubb.lab11.web.converter.CastedSpellConverter;
import ro.ubb.lab11.web.dto.CastedSpellDTO;
import ro.ubb.lab11.web.dto.CastedSpellsDTO;

import java.util.List;

@RestController
public class CastedSpellController {
    public static final Logger logger = LoggerFactory.getLogger(CastedSpellController.class);

    @Autowired
    private ICastedSpellService castedSpellService;
    @Autowired
    private CastedSpellConverter castedSpellConverter;

    @RequestMapping(value = "/casted_spells", method = RequestMethod.GET)
    CastedSpellsDTO getCastedSpellsFromRepository() {
        logger.trace("getAllCastedSpells - method entered");
        List<CastedSpell> casted_spells = castedSpellService.getAllCastedSpells();
        CastedSpellsDTO castedSpellsDTO = new CastedSpellsDTO(castedSpellConverter.convertModelsToDTOs(casted_spells));
        logger.trace("getAllCastedSpells: " + casted_spells);
        return castedSpellsDTO;
    }

    @RequestMapping(value = "/casted_spells", method = RequestMethod.POST)
    CastedSpellDTO addCastedSpell(@RequestBody CastedSpellDTO castedSpellDTO) {
        logger.trace("addCastedSpell - method entered - CastedSpellDTO: " + castedSpellDTO);
        var castedSpell = castedSpellConverter.convertDtoToModel(castedSpellDTO);
        var result = castedSpellService.addCastedSpell(castedSpell.getWizardId(), castedSpell.getSpellId(), castedSpell.getDetails());
        var resultModel = castedSpellConverter.convertModelToDto(result);
        logger.trace("addCastedSpell - CastedSpell added");
        return resultModel;
    }

    @RequestMapping(value = "/casted_spells/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteCastedSpell(@PathVariable Long id) {
        castedSpellService.deleteCastedSpell(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/casted_spells/{id}", method = RequestMethod.PUT)
    CastedSpellDTO updateCastedSpell(@PathVariable Long id, @RequestBody CastedSpellDTO castedSpellDTO) {
        logger.trace("updateCastedSpell - method entered - CastedSpellDTO: " + castedSpellDTO);
        var castedSpell = castedSpellConverter.convertDtoToModel(castedSpellDTO);
        var result = castedSpellService.updateCastedSpell(id, castedSpell.getWizardId(), castedSpell.getSpellId(), castedSpell.getDetails());
        var resultModel = castedSpellConverter.convertModelToDto(result);
        logger.trace("updateCastedSpell - CastedSpell updated");
        return resultModel;
    }
}
