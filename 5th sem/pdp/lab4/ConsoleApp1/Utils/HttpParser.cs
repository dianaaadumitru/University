namespace ConsoleApp1.Utils;

public class HttpParser
{
    public static readonly int HttpPort = 80;

    /**
     * Return the response body from the response content.
     *
     * The response content is composed of:
     *     - response headers
     *     - two new lines (aka one empty line)
     *     - response body
     *
     * If the body has length 0 (in case it is just a HTTP redirect), it will return the empty string.
     */
    public static string GetResponseBody(string responseContent) {
        var responseParts = responseContent.Split(new[] {"\r\n\r\n"}, StringSplitOptions.RemoveEmptyEntries);

        return responseParts.Length > 1 ? responseParts[1] : "";
    }

    /**
     * The header is fully obtained when the server sends two new lines (aka one empty line).
     */
    public static bool ResponseHeaderFullyObtained(string responseContent) {
        return responseContent.Contains("\r\n\r\n");
    }

    /**
     * Parses the HTTP Response content and returns the value of the "Content-Length" header.
     */
    public static int GetContentLength(string responseContent) {
        var contentLength = -1;
        var responseLines = responseContent.Split('\r', '\n');

        foreach (var responseLine in responseLines) {
            // the header is composed using the following pattern:
            // <header_name>:<header_value>
            var headerDetails = responseLine.Split(':');

            if (String.Compare(headerDetails[0], "Content-Length", StringComparison.Ordinal) == 0) {
                contentLength = int.Parse(headerDetails[1]);
            }
        }

        return contentLength;
    }
    
    public static string GetRequestString(string hostname, string endpoint)
    {
        return "GET " + endpoint + " HTTP/1.1\r\n" +
               "Host: " + hostname + "\r\n" + 
               "Content-Length: 0\r\n\r\n";
    }
}