import Model.CastedSpell;
import Model.Pet;
import Model.Spell;
import Model.Wizard;
import Service.*;
import TCP.TcpServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        TcpServer tcpServer = new TcpServer(executorService, ServiceConfig.PORT);
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext("Config");
        WizardService wizardService=context.getBean(WizardService.class);
        SpellService spellService=context.getBean(SpellService.class);
        CastedSpellService castedSpellService=context.getBean(CastedSpellService.class);
        PetService petService=context.getBean(PetService.class);

        //wizard handlers
        tcpServer.addHandler("addWizard", request -> {
            Object obj=request.getBody();
            if(obj instanceof Wizard wizard) {
                try {
                    Future<Wizard> res = wizardService.addWizard(wizard.getId(), wizard.getName(), wizard.getAge(), wizard.getPet());
                    Wizard result = res.get();
                    return new Message(Message.OK, result);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a wizard");
        });

        tcpServer.addHandler("updateWizard", request -> {
            Object obj=request.getBody();
            if(obj instanceof Wizard wizard) {
                Future<Wizard> res = wizardService.updateWizard(wizard.getId(), wizard.getName(), wizard.getAge(), wizard.getPet());
                try {
                    Wizard result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a wizard");
        });

        tcpServer.addHandler("getWizards", request -> {
            Future<List<Wizard>> res = wizardService.getWizards();
                try {
                    List<Wizard> result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
        });

        tcpServer.addHandler("deleteWizard", request -> {
            Object obj=request.getBody();
            if(obj instanceof Long id) {
                Future<Wizard> res = wizardService.deleteWizard(id);
                try {
                    Wizard result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a wizard");
        });

        //spell handlers
        tcpServer.addHandler("addSpell", request -> {
            Object obj=request.getBody();
            if(obj instanceof Spell spell) {
                try {
                    Future<Spell> res = spellService.addSpell(spell.getId(), spell.getName(), spell.getDescription());
                    Spell result = res.get();
                    return new Message(Message.OK, result);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a spell");
        });

        tcpServer.addHandler("updateSpell", request -> {
            Object obj=request.getBody();
            if(obj instanceof Spell spell) {
                Future<Spell> res = spellService.updateSpell(spell.getId(), spell.getName(), spell.getDescription());
                try {
                    Spell result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a spell");
        });

        tcpServer.addHandler("getSpells", request -> {
            Future<List<Spell>> res = spellService.getSpells();
            try {
                List<Spell> result = res.get();
                return  new Message(Message.OK, result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });

        tcpServer.addHandler("deleteSpell", request -> {
            Object obj=request.getBody();
            if(obj instanceof Long id) {
                Future<Spell> res = spellService.deleteSpell(id);
                try {
                    Spell result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a spell");
        });


        //casted spell handlers
        tcpServer.addHandler("addCastedSpell", request -> {
            Object obj=request.getBody();
            if(obj instanceof CastedSpell castedSpell) {
                try {
                    Future<CastedSpell> res = castedSpellService.addCastedSpell(castedSpell.getId().getLeftPart(),castedSpell.getId().getRightPart(),castedSpell.getDetails());
                    CastedSpell result = res.get();
                    return new Message(Message.OK, result);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a casted spell");
        });

        tcpServer.addHandler("updateCastedSpell", request -> {
            Object obj=request.getBody();
            if(obj instanceof CastedSpell castedSpell) {
                Future<CastedSpell> res = castedSpellService.updateCastedSpell(castedSpell.getId().getLeftPart(), castedSpell.getId().getRightPart(), castedSpell.getDetails());
                try {
                    CastedSpell result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a casted spell");
        });

        tcpServer.addHandler("getCastedSpells", request -> {
            Future<List<CastedSpell>> res = castedSpellService.getCastedSpells();
            try {
                List<CastedSpell> result = res.get();
                return  new Message(Message.OK, result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });

        tcpServer.addHandler("deleteCastedSpell", request -> {
            Object obj=request.getBody();
            if(obj instanceof CastedSpell castedSpell) {
                Future<CastedSpell> res = castedSpellService.deleteCastedSpell(castedSpell.getId().getLeftPart(), castedSpell.getId().getRightPart());
                try {
                    CastedSpell result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a casted spell");
        });

        //--pet handlers
        tcpServer.addHandler("addPet", request -> {
            Object obj=request.getBody();
            if(obj instanceof Pet pet) {
                try {
                    Future<Pet> res = petService.addPet(pet.getId(), pet.getName(), pet.getBreed(), pet.getWizardId());
                    Pet result = res.get();
                    return new Message(Message.OK, result);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a pet");
        });

        tcpServer.addHandler("updatePet", request -> {
            Object obj=request.getBody();
            if(obj instanceof Pet pet) {
                Future<Pet> res = petService.updatePet(pet.getId(), pet.getName(), pet.getBreed(), pet.getWizardId());
                try {
                    Pet result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a pet");
        });

        tcpServer.addHandler("getPets", request -> {
            Future<List<Pet>> res = petService.getPets();
            try {
                List<Pet> result = res.get();
                return  new Message(Message.OK, result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });

        tcpServer.addHandler("deletePet", request -> {
            Object obj=request.getBody();
            if(obj instanceof Long id) {
                Future<Pet> res = petService.deletePet(id);
                try {
                    Pet result = res.get();
                    return  new Message(Message.OK, result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Message.ERROR, e.getMessage());
                }
            }
            else return new Message(Message.ERROR, "Object not a pet");
        });


        tcpServer.startServer();

        System.out.println("bye server");
    }
}
