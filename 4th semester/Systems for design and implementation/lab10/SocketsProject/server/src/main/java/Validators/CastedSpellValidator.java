package Validators;

import Model.CastedSpell;
import Model.Pair;
import Model.ValidatorException;
import Repository.SpellRepository;
import Repository.WizardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CastedSpellValidator implements Validator<CastedSpell> {
    @Autowired
    private WizardRepository wizardRepository;

    @Autowired
    private SpellRepository spellRepository;

    public CastedSpellValidator(WizardRepository wr, SpellRepository sr){
        this.wizardRepository=wr;
        this.spellRepository=sr;
    }

    @Override
    public void validate(CastedSpell castedSpell) throws ValidatorException {
        Stream.of(new Pair<>(castedSpell.getDetails().equals(""), "Details cannot be empty!"))
                .forEach(invalidSituation -> {
                    if (invalidSituation.getLeftPart()) {
                        var rightPart = invalidSituation.getRightPart();
                        try {
                            throw new ValidatorException(rightPart);
                        } catch (ValidatorException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        var result=this.wizardRepository.findAll().stream()
                .filter(wizard->wizard.getId().equals(castedSpell.getId().getLeftPart()))
                .collect(Collectors.toList());
        if(result.size()==0){
            throw new ValidatorException("Wizard id not valid");
        }
        var result2=this.spellRepository.findAll().stream()
                .filter(spell->spell.getId().equals(castedSpell.getId().getRightPart()))
                .collect(Collectors.toList());
        if(result2.size()==0){
            throw new ValidatorException("Spell id not valid");
        }
    }
}

