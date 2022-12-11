using System.Net;
using System.Net.Sockets;
using System.Text;
using ConsoleApp1.Domain;
using ConsoleApp1.Utils;

namespace ConsoleApp1.Implementation;

public static class TaskMethod {
    private static List<string> HOSTS;

    public static void run(List<string> hostnames) {
        HOSTS = hostnames;

        var tasks = new List<Task>();

        for (var i = 0; i < HOSTS.Count; i++) {
            tasks.Add(Task.Factory.StartNew(doStart, i));
        }

        // Waits for all tasks to complete execution
        Task.WaitAll(tasks.ToArray());
    }

    private static void doStart(object idObject) {
        var id = (int) idObject;

        StartClient(HOSTS[id], id);
    }

    /**
     * Connect to a server.
     */
    private static void StartClient(string host, int id) {
        // establish the remote endpoint of the server
        var ipHostInfo = Dns.GetHostEntry(host.Split('/')[0]);
        var ipAddress = ipHostInfo.AddressList[0];
        var remoteEndpoint = new IPEndPoint(ipAddress, HttpParser.HttpPort);

        // create the TCP/IP socket
        var client = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

        // create a wrapper for the connection information
        var socket = new CustomSocket {
            socket = client,
            hostname = host.Split('/', 2)[0],
            urlPath =  host.Split('/', 2)[1],
            endpoint = host.Contains("/") ? host.Substring(host.IndexOf("/", StringComparison.Ordinal)) : "/",
            remoteEndpoint = remoteEndpoint,
            id = id
        };

        // connect to the remote endpoint  
        ConnectWrapper(socket).Wait();

        // request data from the server
        SendWrapper(socket, HttpParser.GetRequestString(socket.hostname, socket.endpoint)).Wait();

        // receive the response from the server
        ReceiveWrapper(socket).Wait();

        // write the response details to the console
        var headerContentLength = HttpParser.GetContentLength(socket.responseContent.ToString());
        string message = $"Header Content-Length for {socket.hostname}{socket.urlPath}: {headerContentLength} bytes\n";

        if (headerContentLength == -1)
        {
            message = $"Header Content-Length for {socket.hostname}{socket.urlPath}: not specified\n";
        }

        Console.WriteLine(message);
        // release the socket
        client.Shutdown(SocketShutdown.Both);
        client.Close();
    }

    private static Task ConnectWrapper(CustomSocket socket) {
        socket.socket.BeginConnect(socket.remoteEndpoint, ConnectCallback, socket);

        return Task.FromResult(socket.connectDone.WaitOne());
    }

    private static void ConnectCallback(IAsyncResult ar) {
        // retrieve the details from the connection information wrapper
        var socket = (CustomSocket) ar.AsyncState!;
        var clientSocket = socket.socket;
        var clientId = socket.id;
        var hostname = socket.hostname;
        var urlPath = socket.urlPath;

        // complete the connection  
        clientSocket.EndConnect(ar);

        Console.WriteLine("Tasks - {0} --> Socket connected to {1} ({2})", clientId, hostname, urlPath);

        // signal that the connection has been made 
        socket.connectDone.Set();
    }

    private static Task SendWrapper(CustomSocket socket, string data) {
        // convert the string data to byte data using ASCII encoding.  
        var byteData = Encoding.ASCII.GetBytes(data);

        // begin sending the data to the server  
        socket.socket.BeginSend(byteData, 0, byteData.Length, 0, SendCallback, socket);

        return Task.FromResult(socket.sendDone.WaitOne());
    }

    private static void SendCallback(IAsyncResult ar) {
        var socket = (CustomSocket) ar.AsyncState!;
        var clientSocket = socket.socket;
        var clientId = socket.id;

        // complete sending the data to the server  
        var bytesSent = clientSocket.EndSend(ar);
        Console.WriteLine("Tasks - {0} --> Sent {1} bytes to server.", clientId, bytesSent);

        // signal that all bytes have been sent
        socket.sendDone.Set();
    }

    private static Task ReceiveWrapper(CustomSocket socket) {
        // begin receiving the data from the server
        socket.socket.BeginReceive(socket.buffer, 0, CustomSocket.BUFFER_SIZE, 0, ReceiveCallback, socket);

        return Task.FromResult(socket.receiveDone.WaitOne());
    }

    private static void ReceiveCallback(IAsyncResult ar) {
        // retrieve the details from the connection information wrapper
        var socket = (CustomSocket) ar.AsyncState!;
        var clientSocket = socket.socket;

        try {
            // read data from the remote device.  
            var bytesRead = clientSocket.EndReceive(ar);

            // get from the buffer, a number of characters <= to the buffer size, and store it in the responseContent
            socket.responseContent.Append(Encoding.ASCII.GetString(socket.buffer, 0, bytesRead));

            // if the response header has not been fully obtained, get the next chunk of data
            if (!HttpParser.ResponseHeaderFullyObtained(socket.responseContent.ToString())) {
                clientSocket.BeginReceive(socket.buffer, 0, CustomSocket.BUFFER_SIZE, 0, ReceiveCallback, socket);
            } else {
                // header has been fully obtained
                // get the body
                var responseBody = HttpParser.GetResponseBody(socket.responseContent.ToString());

                // the custom header parser is being used to check if the data received so far has the length
                // specified in the response headers
                if (responseBody.Length < HttpParser.GetContentLength(socket.responseContent.ToString())) {
                    // if it isn't, than more data is to be retrieve
                    clientSocket.BeginReceive(socket.buffer, 0, CustomSocket.BUFFER_SIZE, 0, ReceiveCallback, socket);
                } else {
                    // otherwise, all the data has been received  
                    // signal that all bytes have been received  
                    socket.receiveDone.Set();
                }
            }
        } catch (Exception e) {
            Console.WriteLine(e.ToString());
        }
    }
}
