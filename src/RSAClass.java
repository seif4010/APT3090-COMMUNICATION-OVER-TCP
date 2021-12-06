import java.math.BigInteger;
import java.util.Random;

public class RSAClass {
    // Declare variables
    private BigInteger p;

    private BigInteger q;

    private BigInteger N;

    private BigInteger phi;

    private BigInteger e;

    private BigInteger d;

    private int bitlength = 1024;

    private Random r;

    public RSAClass()

    {

        r = new Random();

        p = BigInteger.probablePrime(bitlength / 2, r); // generate a random biginteger p of 512 bits

        q = BigInteger.probablePrime(bitlength / 2, r); // generate a random biginteger q of 512 bits

        N = p.multiply(q); // calculate N

        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(bitlength, r); // generate e the public key which is 1024bits

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)

        {

            e.add(BigInteger.ONE);

        }

        d = e.modInverse(phi); // generate d the private key

    }

    public RSAClass(BigInteger e, BigInteger d, BigInteger N)

    {

        this.e = e;

        this.d = d;

        this.N = N;

    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getN() {
        return N;
    }

    public static String bytesToString(byte[] encrypted)

    {

        String test = "";

        for (byte b : encrypted)

        {

            test += Byte.toString(b);

        }

        return test;

    }

    // Decrypt message

    public byte[] decrypt(byte[] message)

    {

        return (new BigInteger(message)).modPow(d, N).toByteArray();

    }

}
