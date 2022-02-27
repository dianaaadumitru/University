//Intr-o livada cresc meri, peri si ciresi.
//Sa se afiseze toti pomii frunctiferi mai batrini
//de 3 ani.


package View;
import Controller.Controller;
import Model.*;
import Repository.Repo;

import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        Repo repo = new Repo();
        Controller controller = new Controller(repo);
        menu(controller);
    }

    public static void menu(Controller controller) {
        boolean ok = true;
        populateList(controller);
        while (ok) {
            System.out.println("\nMenu:");
            System.out.println("0.Quit");
            System.out.println("1.Add");
            System.out.println("2.Remove");
            System.out.println("3.Filter");
            System.out.println("4. Show list");
            System.out.println(">>");
            Scanner scanner = new Scanner(System.in);
            try {
                int cmd = scanner.nextInt();
                System.out.println(cmd);
                switch (cmd) {
                    case 0:
                        ok = false;
                        System.out.println("Goodbye!");
                        break;
                    case 1:
                        addTreeMenu(controller);
                        break;
                    case 2:
                        removeTreeMenu(controller);
                        break;
                    case 3:
                        filterFruitsMenu(controller);
                        break;
                    case 4:
                        showListMenu(controller);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void showListMenu(Controller controller){
        Entity[] elements = controller.getRepo().getElements();

        for (var it : elements) {
            if (it != null) {
                System.out.println(it.toString());
            }
        }
    }

    public static void addTreeMenu(Controller controller) {
        System.out.println("What kind of tree do you want to add?");
        System.out.println("1. Apple");
        System.out.println("2. Pear");
        System.out.println("3. Cherry");
        Scanner scanner = new Scanner(System.in);
        int kind = scanner.nextInt();
        System.out.println("What is the age of the tree?");
        int age = scanner.nextInt();
        if (kind == 1) {
            try {
                controller.addController(new Mar(age));
                System.out.println("Fruit added\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (kind == 2) {
            try {
                controller.addController(new Par(age));
                System.out.println("Fruit added\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                controller.addController(new Cires(age));
                System.out.println("Fruit added\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void removeTreeMenu(Controller controller){
        System.out.println("What kind of tree do you want to remove?");
        System.out.println("1. Apple");
        System.out.println("2. Pear");
        System.out.println("3. Cherry");
        Scanner scanner = new Scanner(System.in);
        int kind = scanner.nextInt();
        System.out.println("What is the age of the tree?");
        int age = scanner.nextInt();
        if (kind == 1) {
            try {
                controller.removeController(new Mar(age));
                System.out.println("Fruit removed\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (kind == 2) {
            try {
                controller.removeController(new Par(age));
                System.out.println("Fruit removed\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                controller.removeController(new Cires(age));
                System.out.println("Fruit removed\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void filterFruitsMenu(Controller controller){
        System.out.println("What is the min age of the tree?");
        Scanner scanner = new Scanner(System.in);
        int age = scanner.nextInt();
        Entity[] elements = controller.getEntitiesOlderThanController(age);

        for (var it : elements) {
            if (it != null) {
                System.out.println(it.toString());
            }
        }
    }

    public static void populateList(Controller controller){
        try{
            controller.addController(new Cires(2));
            controller.addController(new Mar(4));
            controller.addController(new Mar(24));
            controller.addController(new Par(4));
            controller.addController(new Mar(1));
            controller.addController(new Cires(6));
            controller.addController(new Cires(3));
            controller.addController(new Par(36));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
//    private final Controller controller;
//
//    public View(Controller controller) {
//        this.controller = controller;
//    }
//
//    public void run(){
//        this.controller.addController(new Cires(2));
//        this.controller.addController(new Mar(4));
//        this.controller.addController(new Mar(24));
//        this.controller.addController(new Par(4));
//        this.controller.addController(new Mar(1));
//        this.controller.addController(new Cires(6));
//        this.controller.addController(new Cires(3));
//        this.controller.addController(new Par(36));
//
//        Entity[] elements = this.controller.getEntitiesOlderThanController(3);
//
//        for (var it : elements) {
//            if (it != null) {
//                System.out.println(it.toString());
//            }
//        }
//    }

