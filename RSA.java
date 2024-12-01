import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class RSA {
    public static void main(String[] args) {
        encryption();
    }
    private static void encryption() {
        System.out.println("Inserisci una parola o una frase:");
        Scanner inp = new Scanner(System.in);
        String parola = inp.nextLine();
        Random rnd = new Random();
        BigInteger p = BigInteger.probablePrime(512, rnd);
        BigInteger q = BigInteger.probablePrime(512, rnd);
        if (p.gcd(q) != BigInteger.ONE) {
            p = BigInteger.probablePrime(512, rnd);
            q = BigInteger.probablePrime(512, rnd);
        }
        BigInteger fEulero = p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE)));
        BigInteger e = generateE(fEulero, rnd);
        BigInteger n = p.multiply(q);
        BigInteger d;
        d = e.modInverse(fEulero);
        ArrayList<Integer> converted = convert(parola);
        System.out.println("Messaggio convertito: " + converted);
        ArrayList<BigInteger> encrypted = encrypt(converted, e, n);
        System.out.println("Messaggio cifrato: " + encrypted);
        ArrayList<Character> decrypted = decrypt(encrypted, d, n);
        System.out.println("Messaggio decifrato: " + decrypted);
        inp.close();
    }
    private static BigInteger generateE(BigInteger fEulero, Random rnd) {
        BigInteger e;
        do {
            e = new BigInteger(512, rnd);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(fEulero) >= 0
                || !e.gcd(fEulero).equals(BigInteger.ONE));
        return e;
    }
    public static ArrayList<Integer> convert(String parola) {
        ArrayList<Integer> converted = new ArrayList<Integer>();
        for (int i = 0; i < parola.length(); i++) {
            char lettera = parola.charAt(i);
            int x = lettera;
            converted.add(x);
        }
        return (converted);
    }
    private static ArrayList<BigInteger> encrypt(ArrayList<Integer> data, BigInteger e, BigInteger n) {
        ArrayList<BigInteger> encryptedValues = new ArrayList<BigInteger>();
        for (Integer value : data) {
            BigInteger m = BigInteger.valueOf(value);
            BigInteger cipher = m.modPow(e, n);
            encryptedValues.add(cipher);
        }
        return encryptedValues;
    }
    public static ArrayList<Character> decrypt(ArrayList<BigInteger> encryptedData, BigInteger d, BigInteger n) {
        ArrayList<Character> decryptedValues = new ArrayList<Character>();
        for (BigInteger cipheredValue : encryptedData) {
            BigInteger decryptedValue = cipheredValue.modPow(d, n);
            decryptedValues.add((char) decryptedValue.intValue());
        }
        return decryptedValues;
    }
}
