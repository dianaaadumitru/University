package ro.ubb.lab11.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.lab11.client.UI.UI;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ro.ubb.lab11.client.config");
        UI console = context.getBean(UI.class);
        console.runProgram();
        System.out.println("goodbye");
    }
}
