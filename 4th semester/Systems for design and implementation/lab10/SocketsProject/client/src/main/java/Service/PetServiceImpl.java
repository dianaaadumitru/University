package Service;

import Model.Pet;
import Model.ValidatorException;
import TCP.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class PetServiceImpl implements PetService{
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private TcpClient tcpClient;

    public PetServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<Pet> addPet(Long id, String name, String breed, Long wid) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(PetService.ADD, new Pet(id, name,breed, wid));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Pet) result);
        });
    }

    @Override
    public Future<Pet> deletePet(Long id) {
        return executorService.submit(() -> {
            Message request = new Message(PetService.DELETE, id);

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Pet) result);
        });
    }

    @Override
    public Future<Pet> updatePet(Long id, String name, String breed, Long wid) {
        return executorService.submit(() -> {
            Message request = new Message(PetService.UPDATE, new Pet(id, name,breed, wid));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Pet) result);
        });
    }

    @Override
    public Future<List<Pet>> getPets() {
        return executorService.submit(() -> {
            Message request = new Message(PetService.GET, new ArrayList<>());

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((List<Pet>) result);
        });
    }
}

