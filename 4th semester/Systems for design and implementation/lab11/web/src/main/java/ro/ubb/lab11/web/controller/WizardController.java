package ro.ubb.lab11.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.lab11.core.model.Pet;
import ro.ubb.lab11.core.model.Wizard;
import ro.ubb.lab11.core.service.IPetService;
import ro.ubb.lab11.core.service.IWizardService;
import ro.ubb.lab11.web.converter.WizardConverter;
import ro.ubb.lab11.web.dto.WizardDTO;
import ro.ubb.lab11.web.dto.WizardsDTO;
import ro.ubb.lab11.web.dto.WizardsPetsDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WizardController {
    public static final Logger logger = LoggerFactory.getLogger(WizardController.class);

    @Autowired
    private IWizardService wizardService;
    @Autowired
    WizardConverter wizardConverter;
    @Autowired
    private IPetService petService;

    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    WizardsDTO getAllWizards() {
        logger.trace("getAllWizards: ");
        List<Wizard> wizards = wizardService.getAllWizards();
        WizardsDTO wizardsDTO = new WizardsDTO(wizardConverter.convertModelsToDTOs(wizards));
        logger.trace("wizards: " + wizardsDTO);
        return wizardsDTO;
    }

    @RequestMapping(value = "/wizards", method = RequestMethod.POST)
    WizardDTO addWizard(@RequestBody WizardDTO wizardDTO) {
        logger.trace("addWizard - WizardDTO: " + wizardDTO);
        var wizard = wizardConverter.convertDtoToModel(wizardDTO);
        var result = wizardService.addWizard(wizard.getName(), wizard.getAge(), wizard.getPet());
        var resultModel = wizardConverter.convertModelToDto(result);
        logger.trace("addWizard - wizard added");
        return resultModel;
    }

    @RequestMapping(value = "/wizards/{id}", method = RequestMethod.PUT)
    WizardDTO updateWizard(@PathVariable Long id, @RequestBody WizardDTO wizardDTO) {
        logger.trace("updateWizard - WizardDTO: " + wizardDTO);
        var wizard = wizardConverter.convertDtoToModel(wizardDTO);
        var result = wizardService.updateWizard(id, wizard.getName(), wizard.getAge(), wizard.getPet());
        var resultModel = wizardConverter.convertModelToDto(result);
        logger.trace("updateWizard - wizard updated");
        return resultModel;
    }

    @RequestMapping(value = "/wizards/filter/{name}")
    WizardsDTO findWizardByName(@PathVariable String name) {
        logger.trace("findWizardByName - name = {}", name);
        List<Wizard> wizards = wizardService.findWizardsByName(name);
        WizardsDTO wizardsDTO = new WizardsDTO(wizardConverter.convertModelsToDTOs(wizards));
        logger.trace("getAllWizards: " + wizards);
        return wizardsDTO;
    }

    @RequestMapping(value = "/wizards/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteWizard(@PathVariable Long id) {
        wizardService.deleteWizard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/wizards/sort", method = RequestMethod.GET)
    WizardsDTO getWizardsSortedByName() {
        List<Wizard> wizards = wizardService.findAllByOrderByAgeAsc();
        WizardsDTO dtos = new WizardsDTO(wizardConverter.convertModelsToDTOs(wizards));
        return dtos;

    }

    @RequestMapping(value = "wizards/pets", method = RequestMethod.GET)
    List<WizardsPetsDTO> getPetsForWizards() {
        List<WizardsPetsDTO> result = new ArrayList<>();
        for(Wizard wizard: wizardService.getAllWizards()) {
            var noPets = 0;
            for(Pet pet: petService.getAllPets()) {
                if (pet.getWid().equals(wizard.getId())) {
                    noPets += 1;
                }
            }
            result.add(new WizardsPetsDTO(wizard.getName(), noPets));
        }
        return result;
    }
}
