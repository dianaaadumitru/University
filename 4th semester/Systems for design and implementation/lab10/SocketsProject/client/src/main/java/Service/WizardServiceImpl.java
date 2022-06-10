package Service;

import Model.Wizard;
import TCP.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class WizardServiceImpl implements WizardService {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private TcpClient tcpClient;

    public WizardServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<Wizard> addWizard(Long id, String name, Integer age, String pet) {
        //build a request of type Service.Message
        //send the request and receive a response of type Service.Message
        //extract result from response
        return executorService.submit(() -> {
            Message request = new Message(WizardService.ADD, new Wizard(id, name, age, pet));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Wizard) result);
        });
    }

    @Override
    public Future<Wizard> deleteWizard(Long id) {
        return executorService.submit(() -> {
            Message request = new Message(WizardService.DELETE, id);

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Wizard) result);
        });
    }

    @Override
    public Future<Wizard> updateWizard(Long id, String name, Integer age, String pet) {
        return executorService.submit(() -> {
            Message request = new Message(WizardService.UPDATE, new Wizard(id, name, age, pet));

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((Wizard) result);
        });
    }

    @Override
    public Future<List<Wizard>> getWizards() {
        return executorService.submit(() -> {
            Message request = new Message(WizardService.GET, new ArrayList<>());

            Message response = tcpClient.sendAndReceive(request);

            Object result = response.getBody();
            //todo: handle exceptions e.g. status error

            return ((List<Wizard>) result);
        });
    }

}
