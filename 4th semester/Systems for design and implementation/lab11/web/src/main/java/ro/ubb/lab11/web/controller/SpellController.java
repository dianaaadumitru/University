package ro.ubb.lab11.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.lab11.core.model.Spell;
import ro.ubb.lab11.core.service.ISpellService;
import ro.ubb.lab11.web.converter.SpellConverter;
import ro.ubb.lab11.web.dto.SpellDTO;
import ro.ubb.lab11.web.dto.SpellsDTO;

import java.util.List;

@RestController
public class SpellController {
    public static final Logger logger = LoggerFactory.getLogger(SpellController.class);

    @Autowired
    private ISpellService spellService;
    @Autowired
    SpellConverter spellConverter;

    @RequestMapping (value = "/spells", method = RequestMethod.GET)
    SpellsDTO getAllSpells() {
        logger.trace("getAllSpells: ");
        List<Spell> spells = spellService.getAllSpells();
        SpellsDTO spellsDTO = new SpellsDTO(spellConverter.convertModelsToDTOs(spells));
        logger.trace("spells " + spellsDTO);

        return spellsDTO;
    }

//    @GetMapping ( "/spells")
//    SpellsDTO getAllSpells() {
//        logger.trace("getAllSpells: ");
//        List<Spell> spells = spellService.getAllSpells();
//        SpellsDTO spellsDTO = new SpellsDTO(spellConverter.convertModelsToDTOs(spells));
//        logger.trace("spells " + spellsDTO);
//
//        return spellsDTO;
//    }

    @RequestMapping (value="/spells", method = RequestMethod.POST)
    SpellDTO addSpell(@RequestBody SpellDTO spellDTO) {
        logger.trace("addSpell - method entered - SpellDTO: " + spellDTO);
        var spell = spellConverter.convertDtoToModel(spellDTO);
        var result = spellService.addSpell(spell.getName(), spell.getDescription());
        var resultModel = spellConverter.convertModelToDto(result);
        logger.trace("addSpell - Spell added");
        return resultModel;
    }

    @RequestMapping(value = "/spells/{id}", method = RequestMethod.PUT)
    SpellDTO updateSpell(@PathVariable Long id, @RequestBody SpellDTO spellDTO) {
        logger.trace("updateSpell - SpellDTO: " + spellDTO);
        var spell = spellConverter.convertDtoToModel(spellDTO);
        var result = spellService.updateSpell(id, spell.getName(), spell.getDescription());
        var resultModel = spellConverter.convertModelToDto(result);
        logger.trace("updateSpell - Spell updated");
        return resultModel;
    }

    @RequestMapping(value = "/spells/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteSpell(@PathVariable Long id) {
        spellService.deleteSpell(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
