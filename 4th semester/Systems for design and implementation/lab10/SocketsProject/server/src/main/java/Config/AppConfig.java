package Config;

import Repository.CastedSpellRepository;
import Repository.PetRepository;
import Repository.SpellRepository;
import Repository.WizardRepository;
import Service.*;
import Validators.CastedSpellValidator;
import Validators.PetValidator;
import Validators.SpellValidator;
import Validators.WizardValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {
    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    PetValidator petValidator(){return new PetValidator(wizardRepository());}

    @Bean
    PetRepository petRepository(){return new PetRepository();}

    @Bean
    PetService petService(){
        return new PetServiceImpl(executorService(), petRepository());
    }

    @Bean
    WizardValidator wizardValidator(){
        return new WizardValidator();
    }

    @Bean
    WizardRepository wizardRepository() {
        return new WizardRepository();
    }

    @Bean
    WizardService wizardService() {
        return new WizardServiceImpl(executorService(), wizardRepository());
    }

    @Bean
    SpellValidator spellValidator(){
        return new SpellValidator();
    }

    @Bean
    SpellRepository spellRepository() {
        return new SpellRepository();
    }

    @Bean
    SpellService spellService() {
        return new SpellServiceImpl(executorService(), spellRepository());
    }

    @Bean
    CastedSpellValidator castedSpellValidator(){
        return new CastedSpellValidator(wizardRepository(), spellRepository());
    }

    @Bean
    CastedSpellRepository castedSpellRepository(){
        return new CastedSpellRepository();
    }

    @Bean
    CastedSpellService castedSpellService(){
        return new CastedSpellServiceImpl(executorService(), castedSpellRepository());
    }

}
