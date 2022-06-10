package Service;

import Model.Spell;
import Model.ValidatorException;

import java.util.List;
import java.util.concurrent.Future;

public interface SpellService {
    String ADD = "addSpell";
    String DELETE = "deleteSpell";
    String UPDATE = "updateSpell";
    String GET = "getSpells";

    Future<Spell> addSpell(Long id, String name, String description) throws ValidatorException;
    Future<Spell> deleteSpell(Long id);
    Future<Spell> updateSpell(Long id, String name, String description);
    Future<List<Spell>> getSpells();
}
