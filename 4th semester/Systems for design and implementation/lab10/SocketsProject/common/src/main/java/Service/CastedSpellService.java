package Service;

import Model.CastedSpell;

import java.util.List;
import java.util.concurrent.Future;

public interface CastedSpellService {
    String ADD = "addCastedSpell";
    String DELETE = "deleteCastedSpell";
    String UPDATE = "updateCastedSpell";
    String GET = "getCastedSpells";

    Future<CastedSpell> addCastedSpell(long id, long id2, String description);
    Future<CastedSpell> updateCastedSpell(long id, long id2, String description);
    Future<CastedSpell> deleteCastedSpell(long id, long id2);
    Future<List<CastedSpell>> getCastedSpells();
}
