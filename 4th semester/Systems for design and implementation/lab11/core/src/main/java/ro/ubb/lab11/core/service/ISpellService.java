package ro.ubb.lab11.core.service;

import ro.ubb.lab11.core.model.Spell;
import ro.ubb.lab11.core.model.Wizard;

import java.util.List;

public interface ISpellService {
    List<Spell> getAllSpells();
    Spell addSpell(String name, String description);
    void deleteSpell(Long id);
    Spell updateSpell(Long id, String name, String description);
}
