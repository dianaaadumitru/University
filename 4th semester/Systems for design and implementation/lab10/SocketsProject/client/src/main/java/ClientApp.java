//import UI.ClientConsole;

import UI.ClientConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //client.TCP.TcpClient tcpClient = new client.TCP.TcpClient(executorService);
        //WizardService wizardService = new client.Service.WizardServiceImpl(executorService, tcpClient);
        //client.UI.ClientConsole clientConsole = new client.UI.ClientConsole(wizardService);

        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext("Config");

        ClientConsole clientConsole=context.getBean(ClientConsole.class);
        clientConsole.runConsole();

        System.out.println("bye client");

//        executorService.shutdown();
    }
}
