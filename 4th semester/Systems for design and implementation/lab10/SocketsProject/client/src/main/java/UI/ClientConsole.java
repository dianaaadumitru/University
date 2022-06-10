package UI;

import Model.*;

import Service.CastedSpellService;
import Service.PetService;
import Service.SpellService;
import Service.WizardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;

public class ClientConsole {
    @Autowired
    private WizardService wizardService;

    @Autowired
    private SpellService spellService;

    @Autowired
    private CastedSpellService castedSpellService;

    @Autowired
    private PetService petService;

    public ClientConsole(WizardService ws, SpellService ss, CastedSpellService cs, PetService ps) {
        this.wizardService = ws;
        this.spellService=ss;
        this.castedSpellService=cs;
        this.petService=ps;
    }

    public static void showMenu() {
        System.out.println("Welcome to Hogwarts!");
        System.out.println("0. Exit application");
        System.out.println("1. See all wizards");
        System.out.println("2. Add a wizard");
        System.out.println("3. Update a wizard");
        System.out.println("4. Delete a Wizard");
        System.out.println("5. See all spells");
        System.out.println("6. Add a spell");
        System.out.println("7. Update a spell");
        System.out.println("8. Delete a spell");
        System.out.println("9. See all casted spells");
        System.out.println("10. Add a casted spell");
        System.out.println("11. Update a casted spell");
        System.out.println("12. Delete casted spell");
        System.out.println("13. See all pets");
        System.out.println("14. Adopt a pet (add)");
        System.out.println("15. Donate a pet (update)");
        System.out.println("16. Diciest pet (delete)");
    }

    public void runConsole() {
        boolean finished = false;
        showMenu();
        System.out.println(">");
        while (!finished) {

            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            switch (option) {
                case 0 -> {
                    finished = true;
                }
                case 1 -> showWizards();
                case 2 -> addWizard();
                case 3 -> updateWizard();
                case 4 -> deleteWizard();
                case 5->showSpells();
                case 6->addSpell();
                case 7->updateSpell();
                case 8->deleteSpell();
                case 9->showCastedSpells();
                case 10->addCastedSpell();
                case 11->updateCastedSpell();
                case 12->deleteCastedSpell();
                case 13->showPets();
                case 14->addPet();
                case 15->updatePet();
                case 16->deletePet();
            }

            showMenu();
            System.out.println(">");
        }

    }

    //wizard info
    private void showWizards() {
        Future<List<Wizard>> resultFuture = wizardService.getWizards(); //non-blocking
        System.out.println(resultFuture);
    }

    private void addWizard() {
        Scanner wizardScanner = new Scanner(System.in);
        System.out.println("Give an id");
        var id = wizardScanner.nextLong();
        System.out.println("Give a name: ");
        var name = wizardScanner.next();
        System.out.println("Give an age: ");
        var age = wizardScanner.nextInt();
        System.out.println("Give a pet name: ");
        var petName = wizardScanner.next();

        try {
            Future<Wizard> resultFuture = wizardService.addWizard(id, name, age, petName); //non-blocking
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }


    private void updateWizard() {
        Scanner wizardScanner = new Scanner(System.in);
        System.out.println("Give an id");
        var id = wizardScanner.nextLong();
        System.out.println("Give a name: ");
        var name = wizardScanner.next();
        System.out.println("Give an age: ");
        var age = wizardScanner.nextInt();
        System.out.println("Give a pet name: ");
        var petName = wizardScanner.next();

        Future<Wizard> resultFuture = wizardService.updateWizard(id, name, age, petName); //non-blocking
    }

    private void deleteWizard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give an id");
        var id = scanner.nextLong();
        Future<Wizard> resultFuture = wizardService.deleteWizard(id); //non-blocking
    }

    //spell info
    private void showSpells() {
        Future<List<Spell>> resultFuture = spellService.getSpells(); //non-blocking
        System.out.println(resultFuture);
    }

    private void addSpell() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give an id: ");
        var id = scanner.nextLong();
        System.out.println("Give a name: ");
        var name = scanner.next();
        System.out.println("Give an description: ");
        var description = scanner.next();

        try {
            Future<Spell> resultFuture =spellService.addSpell(id, name, description);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateSpell() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give an id");
        var id = scanner.nextLong();
        System.out.println("Give a name: ");
        var name = scanner.next();
        System.out.println("Give an description: ");
        var description = scanner.next();
        try {
            Future<Spell> resultFuture = spellService.updateSpell(id, name, description);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteSpell() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give an id");
        var id = scanner.nextLong();
        Future<Spell> resultFuture = this.spellService.deleteSpell(id);
    }

    //casted spell info
    private void showCastedSpells() {
        Future<List<CastedSpell>> resultFuture = castedSpellService.getCastedSpells(); //non-blocking
        System.out.println(resultFuture);
    }

    private void addCastedSpell() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give wizard id: ");
        var id = scanner.nextLong();
        System.out.println("Give spell id: ");
        var id2 = scanner.nextLong();
        System.out.println("Give a description: ");
        var description = scanner.next();

        try {
            Future<CastedSpell> resultFuture=this.castedSpellService.addCastedSpell(id, id2, description);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCastedSpell() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give wizard id: ");
        var id = scanner.nextLong();
        System.out.println("Give spell id: ");
        var id2 = scanner.nextLong();
        System.out.println("Give a description: ");
        var description = scanner.next();
        try {
            Future<CastedSpell> resultFuture=this.castedSpellService.updateCastedSpell(id, id2, description);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCastedSpell() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give wizard id");
        var id = scanner.nextLong();
        System.out.println("Give spell id");
        var id2 = scanner.nextLong();
        Future<CastedSpell> resultFuture=this.castedSpellService.deleteCastedSpell(id, id2);
    }

    //pet info
    private void showPets() {
        Future<List<Pet>> resultFuture = petService.getPets(); //non-blocking
        System.out.println(resultFuture);
    }

    private void addPet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give an id: ");
        var id = scanner.nextLong();
        System.out.println("Give a name: ");
        var name = scanner.next();
        System.out.println("Give a breed: ");
        var breed = scanner.next();
        System.out.println("Give a wizard id: ");
        var wid = scanner.nextLong();

        try {
            Future<Pet> resultFuture =petService.addPet(id, name, breed, wid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updatePet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give an id");
        var id = scanner.nextLong();
        System.out.println("Give a name: ");
        var name = scanner.next();
        System.out.println("Give a breed: ");
        var breed = scanner.next();
        System.out.println("Give a wizard id: ");
        var wid = scanner.nextLong();
        try {
            Future<Pet> resultFuture = petService.updatePet(id, name, breed, wid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deletePet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give an id");
        var id = scanner.nextLong();
        Future<Pet> resultFuture = this.petService.deletePet(id);
    }
}
