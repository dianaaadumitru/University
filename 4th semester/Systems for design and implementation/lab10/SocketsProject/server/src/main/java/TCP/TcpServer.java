package TCP;

import Service.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TcpServer {
    @Autowired
    private ExecutorService executorService;
    private final int port;
    private final Map<String, UnaryOperator<Message>> methodHandlers;

    public TcpServer(ExecutorService executorService, int port) {
        this.executorService = executorService;
        //The server instantiates a ServerSocket object, specifying which port number communication is to occur on.
        this.port = port;
        this.methodHandlers = new HashMap<>();
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() {
        try (var serverSocket = new ServerSocket(this.port)) {
            System.out.println("server started; waiting for clients...");
            while (true) {
                //The server invokes the accept() method of the ServerSocket class.
                //This method waits until a client connects to the server on the given port.
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");
                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (var is = socket.getInputStream();
                 var os = socket.getOutputStream()) {

                //read the request (of type Service.Message) from client
                Message request = new Message();
                ObjectInputStream ois=new ObjectInputStream(is);
                request.readFrom(ois);
                System.out.println("received request: " + request);

                // compute response (of type Service.Message)
                Message response = methodHandlers.get(request.getHeader()).apply(request);
                System.out.println("computed response: " + response);

                //send response (of type Service.Message) to client
                ObjectOutputStream oos=new ObjectOutputStream(os);
                response.writeTo(oos);
                System.out.println("response sent to client");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
