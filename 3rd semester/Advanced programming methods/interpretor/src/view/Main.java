package view;

import controller.Controller;
import model.ADT.IStack;
import model.ADT.MyDictionary;
import model.ADT.MyList;
import model.ADT.MyStack;
import model.PrgState;
import model.exceptions.MyException;
import model.expression.ArithExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;
import repository.IRepo;
import repository.Repository;

import java.util.Scanner;


public class Main {
    public static void printMenu(){
        System.out.println("\nMenu:");
        System.out.println("0. Exit");
        System.out.println("1. int v; v=2;Print(v);");
        System.out.println("2. int a;int b; a=2+3*5;b=a+1;Print(b);");
        System.out.println("3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v);");
        System.out.println(">>");
    }
    public static void main(String[] args) throws MyException {
        IStack<IStmt> stack1 = new MyStack<>();
        IStack<IStmt> stack2 = new MyStack<>();
        IStack<IStmt> stack3 = new MyStack<>();

        IStmt example_1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        PrgState prg1 = new PrgState(stack1, new MyDictionary<String, Value>(),  new MyList<Value>(), example_1);
        IRepo repo1 = new Repository(prg1);
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


        PrgState prg2= new PrgState(stack2, new MyDictionary<String, Value>(),  new MyList<Value>(), example_2);
        IRepo repo2 = new Repository(prg2);
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

        PrgState prg3 = new PrgState(stack3, new MyDictionary<String, Value>(),  new MyList<Value>(), example_3);
        IRepo repo3 = new Repository(prg3);
        Controller ctr3 = new Controller(repo3);

        repo1.addState(prg1);
        repo2.addState(prg2);
        repo3.addState(prg3);


        Scanner scanner = new Scanner(System.in);
        boolean ok = true;
        while (ok) {
            printMenu();
            int cmd = scanner.nextInt();
            switch (cmd) {
                case 0 -> {
                    ok = false;
                    System.out.println("Goodbye!");
                }
                case 1 -> {
                    ctr1.allStep();
                }
                case 2 -> {
                    ctr2.allStep();
                }
                case 3 -> {
                    ctr3.allStep();
                }
            }
        }
    }
}