import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
public class RSAUnico {
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
        BigInteger d = e.modInverse(fEulero);
        BigInteger message = convertToBigInteger(parola);
        System.out.println("Messaggio convertito in BigInteger: " + message);
        BigInteger encrypted = encrypt(message, e, n);
        System.out.println("Messaggio cifrato: " + encrypted);
        BigInteger decrypted = decrypt(encrypted, d, n);
        String decryptedMessage = convertFromBigInteger(decrypted);
        System.out.println("Messaggio decifrato: " + decryptedMessage);
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
    public static BigInteger convertToBigInteger(String parola) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parola.length(); i++) {
            sb.append((int) parola.charAt(i));
        }
        return new BigInteger(sb.toString());
    }
    public static String convertFromBigInteger(BigInteger bigInteger) {
        String str = bigInteger.toString();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int charCode;
            if (i + 3 <= str.length() && Integer.parseInt(str.substring(i, i + 3)) <= 255) {
                charCode = Integer.parseInt(str.substring(i, i + 3));
                i += 3;
            } else if (i + 2 <= str.length()) {
                charCode = Integer.parseInt(str.substring(i, i + 2));
                i += 2;
            } else {
                break;
            }
            sb.append((char) charCode);
        }
        return sb.toString();
    }
    private static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }
    private static BigInteger decrypt(BigInteger encryptedMessage, BigInteger d, BigInteger n) {
        return encryptedMessage.modPow(d, n);
    }
}