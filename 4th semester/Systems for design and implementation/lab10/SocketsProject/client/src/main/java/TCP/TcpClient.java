package TCP;

import Service.Message;
import Service.ServiceConfig;
import Service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class TcpClient {
    @Autowired
    private ExecutorService executorService;

    public TcpClient(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Message sendAndReceive(Message request) {
        //While the server is waiting, a client instantiates a Socket object,
        //specifying the server name and port number to connect to.
        try (var socket = new Socket(ServiceConfig.HOST, ServiceConfig.PORT);
             var is = socket.getInputStream();
             var os = socket.getOutputStream()) {

            System.out.println("sending request: " + request);
            ObjectOutputStream oos=new ObjectOutputStream(os);
            request.writeTo(oos);
            System.out.println("request sent");

            Message response = new Message();
            ObjectInputStream ois=new ObjectInputStream(is);
            response.readFrom(ois);
            System.out.println("received response: " + response);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("exception in send and receive", e);
        }

    }
}
