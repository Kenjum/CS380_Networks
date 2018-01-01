import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class IPv4Client
{
    private static Socket socket;

    public static void main(String[] args)
    {
        connect();
    }

    /**
     * Connects the client to the server and
     * creates a Listener thread.
     */
    public static void connect()
    {
        String hostName = "18.221.102.182";
        int portNumber = 38003;

        try
        {
            socket = new Socket(hostName, portNumber);
            System.out.println("Connected to server.");
            new Connection(socket).start();
        }
        catch (UnknownHostException e) {
            System.err.println("ERROR: Unknown host " + hostName + ".");
        } catch (Exception e) {
            System.err.println("ERROR: Could not connect to " + hostName + ".");
        }
    }

    /**
     * Disconnects the client from the server.
     */
    public static void disconnect()
    {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage() + ".");
        }
    }
}