using System.Net;
using System.Net.Sockets;
using System.Text;

namespace ConsoleApp1.Domain;

public class CustomSocket
{
    // client socket
    public Socket socket = null;

    // size of receive buffer
    public const int BUFFER_SIZE = 1024;

    // receive buffer  
    public byte[] buffer = new byte[BUFFER_SIZE];

    // received data  
    public StringBuilder responseContent = new StringBuilder();

    // client id
    public int id; // keep a unique id for each custom socket

    // server's hostname
    public string hostname;

    // server's url path
    public string urlPath;

    // request path
    public string endpoint;
        
    // server's ip address
    public IPEndPoint remoteEndpoint; // ip of website endpoint (address+port)

    // mutex for "connect" operation
    public ManualResetEvent connectDone = new ManualResetEvent(false);

    // mutex for "send" operation
    public ManualResetEvent sendDone = new ManualResetEvent(false);

    // mutex for "receive" operation
    public ManualResetEvent receiveDone = new ManualResetEvent(false);
}