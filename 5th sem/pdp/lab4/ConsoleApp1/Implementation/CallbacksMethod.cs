using System.Net;
using System.Net.Sockets;
using System.Text;
using ConsoleApp1.Domain;
using ConsoleApp1.Utils;

namespace ConsoleApp1.Implementation;

public static class CallbacksMethod
{
    private static List<string> HOSTS;

        public static void run(List<string> hostnames) {
            HOSTS = hostnames;

            for (var i = 0; i < HOSTS.Count; i++) {
                doStart(i);
                Thread.Sleep(100);
            }
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
            socket.socket.BeginConnect(socket.remoteEndpoint, Connected, socket);
        }

        private static void Connected(IAsyncResult ar) {
            // retrieve the details from the connection information wrapper
            var socket = (CustomSocket) ar.AsyncState!;
            var clientSocket = socket.socket;
            var clientId = socket.id;
            var hostname = socket.hostname;
            var urlPath = socket.urlPath;

            // complete the connection  
            clientSocket.EndConnect(ar);
            Console.WriteLine("Callbacks - {0} --> Socket connected to {1} ({2})", clientId, hostname, urlPath);

            // convert the string data to byte data using ASCII encoding.  
            var byteData = Encoding.ASCII.GetBytes(HttpParser.GetRequestString(socket.hostname, socket.endpoint));

            // begin sending the data to the server  
            socket.socket.BeginSend(byteData, 0, byteData.Length, 0, Sent, socket);
        }

        private static void Sent(IAsyncResult ar) {
            var socket = (CustomSocket) ar.AsyncState!;
            var clientSocket = socket.socket;
            var clientId = socket.id;

            // complete sending the data to the server  
            var bytesSent = clientSocket.EndSend(ar);
            Console.WriteLine("Callbacks - {0} --> Sent {1} bytes to server.", clientId, bytesSent);

            // receive the response from the server
            // begin receiving the data from the server
            socket.socket.BeginReceive(socket.buffer, 0, CustomSocket.BUFFER_SIZE, 0, Receiving, socket);
        }

        private static void Receiving(IAsyncResult ar) {
            // retrieve the details from the connection information wrapper
            var socket = (CustomSocket) ar.AsyncState!;
            var clientSocket = socket.socket;
            var clientId = socket.id;

            try {
                // read data from the remote device.  
                var bytesRead = clientSocket.EndReceive(ar);

                // get from the buffer, a number of characters <= to the buffer size, and store it in the responseContent
                socket.responseContent.Append(Encoding.ASCII.GetString(socket.buffer, 0, bytesRead));

                // if the response header has not been fully obtained, get the next chunk of data
                if (!HttpParser.ResponseHeaderFullyObtained(socket.responseContent.ToString())) {
                    clientSocket.BeginReceive(socket.buffer, 0, CustomSocket.BUFFER_SIZE, 0, Receiving, socket);
                } else {
                    // header has been fully obtained
                    // get the body
                    var responseBody = HttpParser.GetResponseBody(socket.responseContent.ToString());

                    // the custom header parser is being used to check if the data received so far has the length
                    // specified in the response headers
                    var contentLengthHeaderValue = HttpParser.GetContentLength(socket.responseContent.ToString());
                    if (responseBody.Length < contentLengthHeaderValue) {
                        // if it isn't, than more data is to be retrieve
                        clientSocket.BeginReceive(socket.buffer, 0, CustomSocket.BUFFER_SIZE, 0, Receiving, socket);
                    } else {
                        // otherwise, all the data has been received  
                        // write the response details to the console
                        var headerContentLength = HttpParser.GetContentLength(socket.responseContent.ToString());
                        string message = $"Header Content-Length for {socket.hostname}{socket.urlPath}: {headerContentLength} bytes\n";

                        if (headerContentLength == -1)
                        {
                            message = $"Header Content-Length for {socket.hostname}{socket.urlPath}: not specified\n";
                        }
                        
                        Console.WriteLine(message);
                        
                        // release the socket
                        clientSocket.Shutdown(SocketShutdown.Both);
                        clientSocket.Close();
                    }
                }
            } catch (Exception e) {
                Console.WriteLine(e.ToString());
            }
        }
}