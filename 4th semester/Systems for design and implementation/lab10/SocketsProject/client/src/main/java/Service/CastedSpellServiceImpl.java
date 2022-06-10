package Service;

import Model.CastedSpell;
import TCP.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CastedSpellServiceImpl implements CastedSpellService{
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private TcpClient tcpClient;

    public CastedSpellServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }


    @Override
    public Future<CastedSpell> addCastedSpell(long id, long id2, String description) {
        return executorService.submit(() -> {
            Message request = new Message(CastedSpellService.ADD, new CastedSpell(id, id2, description));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((CastedSpell) result);
        });
    }

    @Override
    public Future<CastedSpell> updateCastedSpell(long id, long id2, String description) {
        return executorService.submit(() -> {
            Message request = new Message(CastedSpellService.UPDATE, new CastedSpell(id, id2, description));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((CastedSpell) result);
        });
    }

    @Override
    public Future<CastedSpell> deleteCastedSpell(long id, long id2) {
        return executorService.submit(() -> {
            Message request = new Message(CastedSpellService.DELETE, new CastedSpell(id,id2,""));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((CastedSpell) result);
        });
    }

    @Override
    public Future<List<CastedSpell>> getCastedSpells() {
        return executorService.submit(() -> {
            Message request = new Message(CastedSpellService.GET, new ArrayList<>());

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((List<CastedSpell>) result);
        });
    }
}
