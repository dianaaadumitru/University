package Service;

import Model.Spell;
import Model.ValidatorException;
import TCP.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SpellServiceImpl implements SpellService{
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private TcpClient tcpClient;

    public SpellServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<Spell> addSpell(Long id, String name, String description) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(SpellService.ADD, new Spell(id, name,description));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Spell) result);
        });
    }

    @Override
    public Future<Spell> deleteSpell(Long id) {
        return executorService.submit(() -> {
            Message request = new Message(SpellService.DELETE, id);

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Spell) result);
        });
    }

    @Override
    public Future<Spell> updateSpell(Long id, String name, String description) {
        return executorService.submit(() -> {
            Message request = new Message(SpellService.UPDATE, new Spell(id, name,description));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Spell) result);
        });
    }

    @Override
    public Future<List<Spell>> getSpells() {
        return executorService.submit(() -> {
            Message request = new Message(SpellService.GET, new ArrayList<>());

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((List<Spell>) result);
        });
    }
}
