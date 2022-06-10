package Service;

import Model.CastedSpell;
import Model.Pair;
import Repository.CastedSpellRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CastedSpellServiceImpl implements CastedSpellService {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private CastedSpellRepository castedSpellRepository;

    public CastedSpellServiceImpl(ExecutorService executorService, CastedSpellRepository repo) {
        this.executorService = executorService;
        this.castedSpellRepository = repo;
    }

    @Override
    public Future<CastedSpell> addCastedSpell(long id, long id2, String description) {
        return executorService.submit(() -> this.castedSpellRepository.save(new CastedSpell(id, id2, description)));
    }

    @Override
    public Future<CastedSpell> updateCastedSpell(long id, long id2, String description) {
        return executorService.submit(() -> this.castedSpellRepository.update(new CastedSpell(id, id2, description)));
    }

    @Override
    public Future<CastedSpell> deleteCastedSpell(long id, long id2) {
        return executorService.submit(() -> this.castedSpellRepository.delete(new Pair<>(id, id2)));
    }

    @Override
    public Future<List<CastedSpell>> getCastedSpells() {
        return executorService.submit(() -> this.castedSpellRepository.findAll());
    }
}
