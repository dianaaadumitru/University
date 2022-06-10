package ro.ubb.lab11.core.service;

import ro.ubb.lab11.core.model.CastedSpell;

import java.util.List;

public interface ICastedSpellService {
    List<CastedSpell> getAllCastedSpells();
    CastedSpell addCastedSpell(Long wizardId, Long spellId, String details);
    void deleteCastedSpell(Long id);
    CastedSpell updateCastedSpell(Long id, Long wizardId, Long spellId, String details);
}
