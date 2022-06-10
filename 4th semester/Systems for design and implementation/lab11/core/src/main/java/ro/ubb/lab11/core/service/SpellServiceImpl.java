package ro.ubb.lab11.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lab11.core.exceptions.MagicException;
import ro.ubb.lab11.core.model.Spell;
import ro.ubb.lab11.core.model.Wizard;
import ro.ubb.lab11.core.model.validators.SpellValidator;
import ro.ubb.lab11.core.repository.ICastedSpellRepository;
import ro.ubb.lab11.core.repository.ISpellRepository;

import java.util.List;

@Service
public class SpellServiceImpl implements ISpellService{
    public static final Logger logger = LoggerFactory.getLogger((SpellServiceImpl.class));

    @Autowired
    private ISpellRepository spellRepo;
    @Autowired
    private SpellValidator validator;
    @Autowired
    private ICastedSpellRepository castedSpellRepository;

    @Override
    public List<Spell> getAllSpells() {
        logger.trace("Get all spells method: ");
        List<Spell> spells = spellRepo.findAll();
        logger.trace("allSpells: " + spells);
        return spells;
    }

    @Override
    public Spell addSpell(String name, String description) {
        logger.trace("addSpell - name: " + name + " - description " + description);
        Spell newSpell = new Spell(name, description);
        validator.validate(newSpell);
        var spell = spellRepo.save(newSpell);
        logger.trace("addSpell - method finished");
        return spell;
    }

    @Override
    public void deleteSpell(Long id) {
        logger.trace("deleteSpell - method entered - id: " + id);
        castedSpellRepository.findAll().stream()
                .filter(castedSpell -> castedSpell.getSpellId().equals(id))
                .findAny()
                .ifPresent(ticket -> {
                    throw new MagicException("The casted spell has a spell!");
                });
        spellRepo.findById(id).ifPresentOrElse((flight) ->spellRepo.deleteById(id),() -> {
            throw new MagicException("Spell does not exist");
        });

        logger.trace("deleteSpell - method finished");
    }

    @Override
    @Transactional
    public Spell updateSpell(Long id, String name, String description) {
        logger.trace("updateSpell - name: " + name + " - description " + description);
        Spell spell = new Spell(name, description);
        spell.setId(id);
        validator.validate(spell);
        spellRepo.findById(id).ifPresentOrElse((spell1) -> {
            spell1.setName(name);
            spell1.setDescription(description);

        }, () -> {
            throw new MagicException("spell does not exist!");
        });
        logger.trace("updateSpell - method finished");
        return spell;
    }
}
