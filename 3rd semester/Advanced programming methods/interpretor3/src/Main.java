//import controller.Controller;
//import model.ADT.IStack;
//import model.ADT.MyDictionary;
//import model.ADT.MyList;
//import model.ADT.MyStack;
//import model.PrgState;
//import model.exceptions.MyException;
//import model.expression.ArithExp;
//import model.expression.ValueExp;
//import model.expression.VarExp;
//import model.statement.*;
//import model.type.BoolType;
//import model.type.IntType;
//import model.type.StringType;
//import model.value.BoolValue;
//import model.value.IntValue;
//import model.value.StringValue;
//import model.value.Value;
//import repository.IRepo;
//import repository.Repository;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.Scanner;
//
//
//public class Main {
//    public static void printMenu(){
//        System.out.println("\nMenu:");
//        System.out.println("0. Exit");
//        System.out.println("1. int v; v=2;Print(v);");
//        System.out.println("2. int a;int b; a=2+3*5;b=a+1;Print(b);");
//        System.out.println("3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v);");
//        System.out.println(">>");
//    }
//    public static void main(String[] args) throws MyException, IOException {
//        IStack<IStmt> stack1 = new MyStack<>();
//        IStack<IStmt> stack2 = new MyStack<>();
//        IStack<IStmt> stack3 = new MyStack<>();
//        IStack<IStmt> stack4 = new MyStack<>();
//
//        IStmt example_1 = new CompStmt(
//                new VarDeclStmt("v", new IntType()),
//                new CompStmt(
//                        new AssignStmt("v", new ValueExp(new IntValue(2))),
//                        new PrintStmt(new VarExp("v"))
//                )
//        );
//
//        PrgState prg1 = new PrgState(stack1, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), example_1);
//        IRepo repo1 = new Repository(prg1, "1.txt");
//        Controller ctr1 = new Controller(repo1);
//
//
//        IStmt example_2 = new CompStmt( new VarDeclStmt("a",new IntType()),
//                new CompStmt(new VarDeclStmt("b",new IntType()),
//                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new
//                                ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*'), '+')),
//                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new
//                                        IntValue(1)), '+')), new PrintStmt(new VarExp("b")))
//                        )
//                )
//        );
//
//
//        PrgState prg2= new PrgState(stack2, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), example_2);
//        IRepo repo2 = new Repository(prg2, "2.txt");
//        Controller ctr2 = new Controller(repo2);
//
//        IStmt example_3 = new CompStmt(
//                new VarDeclStmt("a" , new BoolType()),
//                new CompStmt(new VarDeclStmt("v", new IntType()),
//                        new CompStmt(
//                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                                new CompStmt(
//                                        new IfStmt(
//                                                new VarExp("a"),
//                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
//                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
//                                        ),
//                                        new PrintStmt(new VarExp("v"))
//                                )
//                        )
//                )
//        );
//
//        PrgState prg3 = new PrgState(stack3, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), example_3);
//        IRepo repo3 = new Repository(prg3, "3.txt");
//        Controller ctr3 = new Controller(repo3);
//
//
//        IStmt example_4 = new CompStmt(
//                new VarDeclStmt("fileName", new StringType()),
//                new CompStmt(new AssignStmt("fileName", new ValueExp(new StringValue("test.txt"))),
//                        new CompStmt(new OpenRFileStmt(new VarExp("fileName")),
//                                new CompStmt(new VarDeclStmt("x", new IntType()),
//                                        new CompStmt(new ReadFileStmt(new VarExp("fileName"), "x"),
//                                                new CompStmt(new PrintStmt(new VarExp("x")),
//                                                        new CompStmt(new ReadFileStmt(new VarExp("fileName"), "x"),
//                                                                new CompStmt(new PrintStmt(new VarExp("x")),
//                                                                        new CloseRFileStmt(new VarExp("fileName"))))))))));
//
//        PrgState prg4 = new PrgState(stack4, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), example_4);
//        IRepo repo4 = new Repository(prg4, "4.txt");
//        Controller ctr4 = new Controller(repo4);
//
//
//        repo1.addState(prg1);
//        repo2.addState(prg2);
//        repo3.addState(prg3);
//        repo4.addState(prg4);
//
//
//        Scanner scanner = new Scanner(System.in);
//        boolean ok = true;
//        while (ok) {
//            printMenu();
//            int cmd = scanner.nextInt();
//            switch (cmd) {
//                case 0 -> {
//                    ok = false;
//                    System.out.println("Goodbye!");
//                }
//                case 1 -> {
//                    ctr1.allStep();
//                }
//                case 2 -> {
//                    ctr2.allStep();
//                }
//                case 3 -> {
//                    ctr3.allStep();
//                }
//                case 4 -> {
//                    ctr4.allStep();
//                }
//            }
//        }
//    }
//}

import controller.Controller;
import model.ADT.*;
import model.PrgState;
import model.exceptions.MyException;
import model.expression.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepo;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, MyException {
        IStack<IStmt> stack1 = new MyStack<>();
        IStack<IStmt> stack2 = new MyStack<>();
        IStack<IStmt> stack3 = new MyStack<>();
        IStack<IStmt> stack4 = new MyStack<>();
        IStack<IStmt> stack5 = new MyStack<>();
        IStack<IStmt> stack6 = new MyStack<>();
        IStack<IStmt> stack7 = new MyStack<>();

        IStmt example_1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        PrgState prg1 = new PrgState(stack1, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Value>(), example_1);
        IRepo repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);


        IStmt example_2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new
                                ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*'), '+')),
                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new
                                        IntValue(1)), '+')), new PrintStmt(new VarExp("b")))
                        )
                )
        );


        PrgState prg2= new PrgState(stack2, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Value>(), example_2);
        IRepo repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStmt example_3 = new CompStmt(
                new VarDeclStmt("a" , new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );

        PrgState prg3 = new PrgState(stack3, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Value>(), example_3);
        IRepo repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);


        TextMenu menu = new TextMenu();

        IStmt example_4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));

        PrgState prg4 = new PrgState(stack4, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<Value>(), example_4);
        IRepo repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);


        IStmt example_5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)),
                                                        '+')))))));


        PrgState prg5 = new PrgState(stack5, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_5);
        IRepo repo5 = new Repository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);


        IStmt example_6 = new CompStmt(new VarDeclStmt("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("x"), new ValueExp(new IntValue(0)), ">"), new CompStmt(new PrintStmt(new VarExp("x")), new AssignStmt("x", new ArithExp(new VarExp("x"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStmt(new VarExp("x")))));



        PrgState prg6 = new PrgState(stack6, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_6);
        IRepo repo6 = new Repository(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);


        IStmt example_7 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewHeapStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewHeapStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(
                                                        new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );

        PrgState prg7 = new PrgState(stack7, new MyDictionary<String, Value>(),  new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_7);
        IRepo repo7 = new Repository(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);

        repo1.addState(prg1);
        repo2.addState(prg2);
        repo3.addState(prg3);
        repo4.addState(prg4);
        repo5.addState(prg5);
        repo6.addState(prg6);
        repo7.addState(prg7);

        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",example_1.toString(),ctr1));
        menu.addCommand(new RunExample("2",example_2.toString(),ctr2));
        menu.addCommand(new RunExample("3",example_3.toString(),ctr3));
        menu.addCommand(new RunExample("4",example_4.toString(),ctr4));
        menu.addCommand(new RunExample("5",example_5.toString(),ctr5));
        menu.addCommand(new RunExample("6",example_6.toString(),ctr6));
        menu.addCommand(new RunExample("7",example_7.toString(),ctr7));

        menu.show();
    }
}