import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;


public class RSA {
    public static void main(String[] args) throws Exception {
        Scanner parola = new Scanner(System.in);
        System.out.println("Scrivi una parola:");
        String userName = parola.nextLine();
        encryption();
    }
    private static void encryption(){	
		Random rnd = new Random();
		BigInteger p = BigInteger.probablePrime(Long.BYTES, rnd);
        BigInteger q = BigInteger.probablePrime(Long.BYTES, rnd);
        BigInteger fEulero = p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE)));
        BigInteger e;
		while ((e = BigInteger.probablePrime(Long.BYTES, rnd)).compareTo(fEulero)>0) {
		}
        if (p.gcd(q) != BigInteger.ONE) {
            p = BigInteger.probablePrime(Long.BYTES, rnd);
            q = BigInteger.probablePrime(Long.BYTES, rnd);
        }

        BigInteger n = p.multiply(q);

        while(true){
            BigInteger i;
            BigInteger x = BigInteger.ONE.add(fEulero.multiply(i));
            if (x % e == 0) {
                d = x / e;
                break;
            }
            i = i.add(BigInteger.ONE);
        }
    }
}
