import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class RSA {
        Scanner inp = new Scanner(System.in);
        String parola = inp.nextLine();
    public static void main(String[] args){
        encryption();
    }
    private static void encryption(){	
		Random rnd = new Random();
		BigInteger p = BigInteger.probablePrime(Long.BYTES, rnd);
        BigInteger q = BigInteger.probablePrime(Long.BYTES, rnd);
        if (p.gcd(q) != BigInteger.ONE) {
            p = BigInteger.probablePrime(Long.BYTES, rnd);
            q = BigInteger.probablePrime(Long.BYTES, rnd);
        }
        BigInteger fEulero = p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE)));
        BigInteger e = generateE(fEulero, rnd);
        BigInteger n = p.multiply(q);
        BigInteger d;
        BigInteger i = BigInteger.ZERO;
        while(true){
            BigInteger a = BigInteger.ONE.add(fEulero.multiply(i));
            if (a.mod(e) == BigInteger.ZERO) {
                d = a.divide(e);
                break;
            }
            i.add(BigInteger.ONE);
        }
        String parola = "ciao";
        ArrayList<Integer> converted = convert(parola);
        System.out.println("Messaggio convertito: " + converted);    
        ArrayList<BigInteger> encrypted = encrypt(converted, e, n);
        System.out.println("Messaggio cifrato: " + encrypted);
        ArrayList<Character> decrypted = decrypt(encrypted, d, n);
        System.out.println("Messaggio decifrato: " + decrypted);
    }
    private static BigInteger generateE(BigInteger fEulero, Random rnd) {
        BigInteger e;
        do {
            e = new BigInteger(512, rnd);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(fEulero) >= 0 || !e.gcd(fEulero).equals(BigInteger.ONE));
        return e;
    }
    public static ArrayList<Integer> convert(String parola){
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
