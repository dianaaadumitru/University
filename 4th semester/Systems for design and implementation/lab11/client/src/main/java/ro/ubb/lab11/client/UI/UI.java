package ro.ubb.lab11.client.UI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.lab11.web.dto.*;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

@Component
public class UI {
    @Autowired
    private RestTemplate restTemplate;

    private final Map<Integer, Runnable> menuTable = new HashMap<>();
    String wizardsUrl = "http://localhost:8080/api/wizards";
    String spellsUrl = "http://localhost:8080/api/spells";
    String petsUrl = "http://localhost:8080/api/pets";
    String castedSpellsUrl = "http://localhost:8080/api/casted_spells";

    public static void printMenu() {
        System.out.println("\nWelcome to Hogwarts!");
        System.out.println("0. Exit application");
        System.out.println("1. See all wizards");
        System.out.println("2. Add a wizard");
        System.out.println("3. Delete a Wizard");
        System.out.println("4. Update a wizard");
        System.out.println("5. Filter wizards by name\n");

        System.out.println("6. See all spells");
        System.out.println("7. Add a spell");
        System.out.println("8. Delete a spell");
        System.out.println("9. Update a spell\n");

        System.out.println("10. See all casted spells");
        System.out.println("11. Add a casted spell");
        System.out.println("12. Delete casted spell");
        System.out.println("13. Update a casted spell\n");

        System.out.println("14. See all pets");
        System.out.println("15. Adopt a pet (add)");
        System.out.println("16. Diciest pet (delete)");
        System.out.println("17. Donate a pet (update)");
        System.out.println("18. See all pets sorted by name");
        System.out.print(">>");
    }

    public void runProgram() {
        menuTable.put(1, this::showWizards);
        menuTable.put(2, this::addWizard);
        menuTable.put(3, this::deleteWizard);
        menuTable.put(4, this::updateWizard);
        menuTable.put(5, this::filterWizardsByName);
        menuTable.put(6, this::showSpells);
        menuTable.put(7, this::addSpell);
        menuTable.put(8, this::deleteSpell);
        menuTable.put(9, this::updateSpell);
        menuTable.put(10, this::showCastedSpells);
        menuTable.put(11, this::addCastedSpell);
        menuTable.put(12, this::deleteCastedSpell);
        menuTable.put(13, this::updateCastedSpell);
        menuTable.put(14, this::showPets);
        menuTable.put(15, this::addPet);
        menuTable.put(16, this::deletePet);
        menuTable.put(17, this::updatePet);
        menuTable.put(18, this::showPetsSortedByName);

        while(true) {
            printMenu();
            try {
                int choice = readNumberFromConsole();
                if (choice == 0)
                    break;
                Runnable toRun = menuTable.get(choice);
                if (toRun == null) {
                    System.out.println("Bad choice");
                    continue;
                }
                toRun.run();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Invalid integer");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateCastedSpell() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = Long.parseLong(stdin.nextLine());
        System.out.println("Wizard id: ");
        Long wizardId = Long.parseLong(stdin.nextLine());
        System.out.println("Spell id: ");
        Long spellId = Long.parseLong(stdin.nextLine());
        System.out.println("Details: ");
        String details = stdin.nextLine();
        try{
            CastedSpellDTO castedSpellDTO = new CastedSpellDTO(wizardId, spellId, details);
            restTemplate.put(castedSpellsUrl + "/{id}", castedSpellDTO, id);
            System.out.println("CastedSpell updated successfully.");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update castedSpell!");
        }
    }

    private void deleteCastedSpell() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("CastedSpell id: ");
        Long id = stdin.nextLong();
        try{
            restTemplate.delete(castedSpellsUrl + "/{id}", id);
            System.out.println("CastedSpell deleted successfully");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot delete castedSpell!");
        }
    }

    private void addCastedSpell() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Wizard id: ");
        Long wizardId = Long.parseLong(stdin.nextLine());
        System.out.println("Spell id: ");Long spellId = Long.parseLong(stdin.nextLine());
        System
                .out.println("Details: ");
        String details = stdin.nextLine().strip();
        try{
            CastedSpellDTO castedSpellDTO = new CastedSpellDTO(wizardId, spellId, details);
            restTemplate.postForObject(castedSpellsUrl, castedSpellDTO, CastedSpellDTO.class);
            System.out.println("CastedSpell added successfully");
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStatusCode());
            System.out.println("Cannot add casted spell!");
        }
    }

    private void showCastedSpells() {
        CastedSpellsDTO castedSpellsDTO = restTemplate.getForObject(castedSpellsUrl, CastedSpellsDTO.class);
        assert castedSpellsDTO != null;
        castedSpellsDTO.getCastedSpells().forEach(System.out::println);
    }

    private void addPet() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Name: ");
        String name = stdin.nextLine();
        System.out.println("Breed: ");
        String breed = stdin.nextLine();
        System.out.println("Wizard id: ");
        Long wid = Long.parseLong(stdin.nextLine());
        try{
            PetDTO petDTO = new PetDTO(name, breed, wid);
            restTemplate.postForObject(petsUrl, petDTO, PetDTO.class);
            System.out.println("Pet added successfully");
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStatusCode());
            System.out.println("Cannot add pet!");
        }
    }

    private void updatePet() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = Long.parseLong(stdin.nextLine());
        System.out.println("Name: ");
        String name = stdin.nextLine();
        System.out.println("Breed: ");
        String breed = stdin.nextLine();
        System.out.println("Wizard id: ");
        Long wid = Long.parseLong(stdin.nextLine());

        try {
            PetDTO petDTO = new PetDTO(name, breed, wid);
            restTemplate.put(petsUrl + "/{id}", petDTO, id);
            System.out.println("Pet updated successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update pet!");
        }
    }

    private void showPetsSortedByName() {
        PetsDTO petsDTO = restTemplate.getForObject(petsUrl, PetsDTO.class);
        assert petsDTO != null;
        petsDTO.getPets().forEach(System.out::println);
    }

    private void deletePet() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("id: ");
        Long id = stdin.nextLong();

        try {
            restTemplate.delete(petsUrl + "/{id}", id);
            System.out.println("Pet deleted successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot delete pet!");
        }
    }

    private void showPets() {
        PetsDTO petsDTO = restTemplate.getForObject(petsUrl, PetsDTO.class);
        assert petsDTO != null;
        petsDTO.getPets().forEach(System.out::println);
    }

    private void updateSpell() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = Long.parseLong(stdin.nextLine());
        System.out.println("Name: ");
        String name = stdin.nextLine().strip();
        System.out.println("Description: ");
        String description = stdin.nextLine().strip();
        try{
            SpellDTO spellDTO = new SpellDTO(name, description);
            restTemplate.put(spellsUrl + "/{id}", spellDTO, id);
            System.out.println("Spell updated successfully.");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update spell!");
        }
    }

    private void deleteSpell() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Spell id: ");
        Long id = stdin.nextLong();
        try{
            restTemplate.delete(spellsUrl + "/{id}", id);
            System.out.println("Spell deleted successfully");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot delete spell!");
        }
    }

    private void addSpell() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Name: ");
        String name = stdin.nextLine().strip();
        System.out.println("Description: ");
        String description = stdin.nextLine().strip();
        try{
            SpellDTO spellDTO = new SpellDTO(name, description);
            restTemplate.postForObject(spellsUrl, spellDTO, SpellDTO.class);
            System.out.println("Spell added successfully");
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStatusCode());
            System.out.println("Cannot add spell!");
        }
    }

    private void showSpells() {
        SpellsDTO spellsDTO = restTemplate.getForObject(spellsUrl, SpellsDTO.class);
        assert spellsDTO != null;
        spellsDTO.getSpells().forEach(System.out::println);
    }

    private void filterWizardsByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name: ");
        String name = scanner.next();
        try {
            WizardsDTO wizardsDTO = restTemplate.getForObject(wizardsUrl + "/filter/{name}", WizardsDTO.class, name);
            if (wizardsDTO == null)
                System.out.println("No wizards with the given name!");
            else
                wizardsDTO.getWizards().forEach(System.out::println);
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot get wizards!");
        }
    }

    private void updateWizard() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = Long.parseLong(stdin.nextLine());
        System.out.println("Name: ");
        String name = stdin.nextLine().strip();
        System.out.println("Age: ");
        Integer age = Integer.valueOf(stdin.nextLine().strip());
        System.out.println("Pet: ");
        String pet = stdin.nextLine().strip();
        try{
            WizardDTO wizardDTO = new WizardDTO(name, age, pet);
            restTemplate.put(wizardsUrl + "/{id}", wizardDTO, id);
            System.out.println("Wizard updated successfully.");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update wizard!");
        }
    }

    private void deleteWizard() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Wizard id: ");
        Long id = stdin.nextLong();
        try{
            restTemplate.delete(wizardsUrl + "/{id}", id);
            System.out.println("Wizard deleted successfully");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot delete wizard!");
        }
    }

    int readNumberFromConsole() {
        Scanner stdin = new Scanner(System.in);
        return stdin.nextInt();
    }

    private void addWizard() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Name: ");
        String name = stdin.nextLine().strip();
        System.out.println("Age: ");
        Integer age = Integer.valueOf(stdin.nextLine().strip());
        System.out.println("Pet: ");
        String pet = stdin.nextLine().strip();
        try{
            WizardDTO wizardDTO = new WizardDTO(name, age, pet);
            restTemplate.postForObject(wizardsUrl, wizardDTO, WizardDTO.class);
            System.out.println("Wizard added successfully");
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStatusCode());
            System.out.println("Cannot add wizard!");
        }
    }

    private void showWizards() {
        WizardsDTO wizardsDTO = restTemplate.getForObject(wizardsUrl, WizardsDTO.class);
        assert wizardsDTO != null;
        wizardsDTO.getWizards().forEach(System.out::println);
    }

}
