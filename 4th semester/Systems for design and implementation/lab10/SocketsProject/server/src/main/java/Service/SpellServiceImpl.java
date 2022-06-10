package Service;

import Model.Spell;
import Model.ValidatorException;
import Repository.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SpellServiceImpl implements SpellService {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SpellRepository spellRepository;

    public SpellServiceImpl(ExecutorService executorService, SpellRepository repo) {
        this.executorService = executorService;
        this.spellRepository = repo;
    }

    @Override
    public Future<Spell> addSpell(Long id, String name, String description) throws ValidatorException {
        return executorService.submit(() -> this.spellRepository.save(new Spell(id, name, description)));
    }

    @Override
    public Future<Spell> deleteSpell(Long id) {
        return executorService.submit(() -> this.spellRepository.delete(id));
    }

    @Override
    public Future<Spell> updateSpell(Long id, String name, String description) {
        return executorService.submit(() -> this.spellRepository.update(new Spell(id, name, description)));
    }

    @Override
    public Future<List<Spell>> getSpells() {
        return executorService.submit(this.spellRepository::findAll);
    }
}

