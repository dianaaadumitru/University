package Gui;

import Gui.controller.Controller;
import Gui.model.ADT.MyDictionary;
import Gui.model.exceptions.MyException;
import Gui.model.expression.*;
import Gui.model.statement.*;
import Gui.model.*;
import Gui.model.statement.Semaphore.AcquireStmt;
import Gui.model.statement.Semaphore.CreateSemaphoreStmt;
import Gui.model.statement.Semaphore.ReleaseStmt;
import Gui.model.type.*;
import Gui.model.value.BoolValue;
import Gui.model.value.IntValue;
import Gui.model.value.StringValue;
import Gui.repository.IRepo;
import Gui.repository.Repository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectWindowController implements Initializable {
    @FXML
    private Button selectBttn;
    @FXML
    private ListView<IStmt> selectItemListView;

    private MainWindowController mainWindowController;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    private IStmt selectExample(ActionEvent actionEvent) {
        return selectItemListView.getItems().get(selectItemListView.getSelectionModel().getSelectedIndex());
    }

    private List<IStmt> initExamples(){
        List<IStmt> list = new ArrayList<>();
        IStmt example_1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        IStmt example_2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new
                                ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*'), '+')),
                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new
                                        IntValue(1)), '+')), new PrintStmt(new VarExp("b")))
                        )
                )
        );

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



        IStmt example_5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)),
                                                        '+')))))));


        IStmt example_6 = new CompStmt(new VarDeclStmt("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("x"), new ValueExp(new IntValue(0)), ">"), new CompStmt(new PrintStmt(new VarExp("x")), new AssignStmt("x", new ArithExp(new VarExp("x"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStmt(new VarExp("x")))));

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

        IStmt forked = new CompStmt(new WriteHeapStmt("a",new ValueExp(new IntValue(30))),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),
                        new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a"))))));


        IStmt example_8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),
                                new CompStmt(new NewHeapStmt("a",new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(forked),new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a"))))))
                        )));

        IStmt example_9 = new CompStmt(
                new VarDeclStmt("v", new BoolType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                ));

        //int a; int b; int c;
        //a=1;b=2;c=5;
        //(switch(a*10)
        // (case (b*c) : print(a);print(b))
        // (case (10) : print(100);print(200))
        // (default : print(300)));
        //print(300)

        IStmt example_10 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new VarDeclStmt("c", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))),
                                                new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                        new CompStmt(new SwitchStmt(new ArithExp(new VarExp("a"), new ValueExp(new IntValue(10)), '*'),
                                                                new ArithExp(new VarExp("b"), new VarExp("c"), '*'),
                                                                new ValueExp(new IntValue(10)),
                                                                new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                                                new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))
                                                        ),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        //fork(acquire(cnt);wh(v1,rh(v1)*10);print(rh(v1));release(cnt));
        IStmt forked1 = new CompStmt(new AcquireStmt(new VarExp("cnt")),
                new CompStmt(new WriteHeapStmt("v1",new ArithExp(new ReadHeapExp(new VarExp("v1")),new ValueExp(new IntValue(10)), '*')),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                new ReleaseStmt(new VarExp("cnt"))
                        )
                )
        );

        //fork(acquire(cnt);wh(v1,rh(v1)*10);wh(v1,rh(v1)*2);print(rh(v1));release(cnt));
        IStmt forked2 = new CompStmt(new AcquireStmt(new VarExp("cnt")),
                new CompStmt(new WriteHeapStmt("v1",new ArithExp(new ReadHeapExp(new VarExp("v1")),new ValueExp(new IntValue(10)), '*')),
                        new CompStmt(new WriteHeapStmt("v1", new ArithExp(new ReadHeapExp(new VarExp("v1")),new ValueExp(new IntValue(2)), '*')),
                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                        new ReleaseStmt(new VarExp("cnt")))
                        )
                )
        );

        //Ref int v1; int cnt;
        //new(v1,1);createSemaphore(cnt,rH(v1));
        //fork(acquire(cnt);wh(v1,rh(v1)*10);print(rh(v1));release(cnt));
        //fork(acquire(cnt);wh(v1,rh(v1)*10);wh(v1,rh(v1)*2);print(rh(v1));release(cnt));
        //acquire(cnt);
        //print(rh(v1)-1);
        //release(cnt)
        IStmt example_11 = new CompStmt(new VarDeclStmt("v1",new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(new NewHeapStmt("v1",new ValueExp(new IntValue(1))),
                                new CompStmt(new CreateSemaphoreStmt(new VarExp("cnt"), new ReadHeapExp(new VarExp("v1"))),
                                        new CompStmt(new ForkStmt(forked1),
                                                new CompStmt(new ForkStmt(forked2),
                                                        new CompStmt(new AcquireStmt(new VarExp("cnt")),
                                                                new CompStmt(new PrintStmt(new ArithExp(new ReadHeapExp(new VarExp("v1")),new ValueExp(new IntValue(1)), '-')),
                                                                        new ReleaseStmt(new VarExp("cnt"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );



        list.add(example_1);
        list.add(example_2);
        list.add(example_3);
        list.add(example_4);
        list.add(example_5);
        list.add(example_6);
        list.add(example_7);
        list.add(example_8);
        list.add(example_9);
        list.add(example_10);
        list.add(example_11);

        return list;
    }

    private void displayExamples(){
        List<IStmt> examples = initExamples();
        selectItemListView.setItems(FXCollections.observableArrayList(examples));
        selectItemListView.getSelectionModel().select(0);
        selectBttn.setOnAction(actionEvent -> {
            int index = selectItemListView.getSelectionModel().getSelectedIndex();
            IStmt selectedProgram = selectItemListView.getItems().get(index);
            index++;
            PrgState programState = new PrgState(selectedProgram);
            IRepo repository = new Repository("log" + index + ".txt");
            Controller controller = new Controller(repository);
            controller.addProgram(programState);
            try {
                selectedProgram.typecheck(new MyDictionary<String, Type>());
                mainWindowController.setController(controller);
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
                alert.show();
            }
            mainWindowController.setController(controller);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayExamples();
    }

}