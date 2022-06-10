package Config;

import Service.*;
import TCP.TcpClient;
import UI.ClientConsole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {
    @Bean
    ClientConsole clientConsole() {
        return new ClientConsole(wizardService(), spellService(), castedSpellService(), petService());
    }

    @Bean
    PetService petService() { return new PetServiceImpl(executorService(), tcpClient());}

    @Bean
    WizardService wizardService() {
        return new WizardServiceImpl(executorService(), tcpClient());
    }

    @Bean
    SpellService spellService(){ return new SpellServiceImpl(executorService(),tcpClient());}

    @Bean
    CastedSpellService castedSpellService(){return new CastedSpellServiceImpl(executorService(), tcpClient());}

    @Bean
    ExecutorService executorService(){
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    TcpClient tcpClient(){
        return new TcpClient(executorService());
    }
}

