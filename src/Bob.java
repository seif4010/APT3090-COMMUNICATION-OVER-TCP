import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

// This class is responsible for encrypting the mesage once Public Key is received
class RSAEncrypt {
    public static byte[] encrypt(byte[] message, BigInteger e, BigInteger n) {

        return (new BigInteger(message)).modPow(e, n).toByteArray();

    }
}

public class Bob {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        for (int i = 0; i < 5; i++) {
            // establish socket connection to server
            socket = new Socket("127.0.0.1", 9877);

            // Getting the encryption key e from the server
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Getting E from the Server");

            // read the server's encrption key
            ois = new ObjectInputStream(socket.getInputStream());
            BigInteger encryptionKey = (BigInteger) ois.readObject();

            // Getting the N from the Server
            System.out.println("Getting N from the server");

            // read N from the server
            BigInteger N = (BigInteger) ois.readObject();

            // print e and n (the public key received)
            System.out.println("E From the Server: " + encryptionKey);
            System.out.println("N From the Server: " + N);

            // Bob writes a string to encrypt using InputStream
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            String teststring;
            System.out.println("Enter the plain text:");
            teststring = br.readLine(); // store the text Bob writes in teststring
            System.out.println("Encrypting String: " + teststring);

            // Convert the string to bytes then encrypt it
            System.out.println("String in Bytes: " + RSAClass.bytesToString(teststring.getBytes()));
            byte[] encrypted = RSAEncrypt.encrypt(teststring.getBytes(), encryptionKey, N);

            // Display the encypted string
            System.out.println("Encrypted String: " + encrypted);

            // Send the encrypted string to Alice(the server)
            oos.writeObject(encrypted);

            // Blank lines
            System.out.println();
            System.out.println();
            System.out.println();

            // close resources
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
    }
}
