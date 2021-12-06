import java.io.*;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class Alice {

    // static ServerSocket variable
    private static ServerSocket server;
    // socket server port on which it will listen
    private static int port = 9877;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        // create the socket server object
        server = new ServerSocket(port);
        // create an RSA object
        RSAClass rsa = new RSAClass();

        // keep listening indefinitely until receives 'exit' call or program terminates
        while (true) {
            System.out.println("Waiting for the client request");
            // creating socket and waiting for client connection
            Socket socket = server.accept();

            // create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            // write encryption object(Public Key) to Socket
            System.out.println("Sending E...");
            oos.writeObject(rsa.getE());
            System.out.println("Sending N...");
            oos.writeObject(rsa.getN());

            // read from socket the encrypted message from Bob to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Store the ciphertext in ebcryptedMessage
            byte[] encryptedMessage = (byte[]) ois.readObject();

            // Print the ciphertext received from Bob
            System.out.println("Encrypted Message Received: " + encryptedMessage);

            // Decrypt Bob's Ciphertext
            byte[] decrypted = rsa.decrypt(encryptedMessage);

            // Print the plain text
            System.out.println("Decrypting Bytes: " + RSAClass.bytesToString(decrypted));
            System.out.println("Decrypted String: " + new String(decrypted));

            // Blank lines
            System.out.println();
            System.out.println();
            System.out.println();

            // Close resources
            ois.close();
            oos.close();
            socket.close();
            // terminate the server if client sends exit request
            if (RSAClass.bytesToString(decrypted).equalsIgnoreCase("exit"))
                break;
        }
        System.out.println("Shutting down Socket server!!");
        // close the ServerSocket object
        server.close();
    }

}