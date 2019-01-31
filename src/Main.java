/*
 * Blockchain Puzzle: Main.java
 * Homework 2 - Problem 2
 * Group 9
 */


/*
 * RESOURCES USED:
 *
 * https://www.baeldung.com/java-byte-arrays-hex-strings (Message Digest Usage for SHA-256)
 * https://www.baeldung.com/sha-256-hashing-java (bytesToHex Function **NO LONGER USED**)
 * https://stackoverflow.com/questions/732034/getting-unixtime-in-java (Getting unix time)
 * https://stackoverflow.com/questions/4400774/java-calculate-hex-representation-of-a-sha-1-digest-of-a-string  (Message Digest General Usage)
 *
 */

/*
 * Some things to note:
 *      1. difficulty = 64 length hex representation
 */

/*
 * Some things to consider:
 *      1. Placing in difficulty in an easy way, how can we streamline that?
 *      2. Do we want uppercase or lowercase for hashes (matters for comparison)
 *      3. How can we make the while loop better?
 */

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Main {

    private static final String UTF8 = "utf8";
    private static final String SHA_256 = "SHA-256";

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        int numberOfNoncesChecked = 0;

        MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
        BlockchainHeader header = new BlockchainHeader("0000004011111111111111111111111111111111111111111111111111111111");
        Random rand = new Random();

        while (Boolean.TRUE) {
            numberOfNoncesChecked++;

            String nonce = getNonce(rand);
            String hashValue = getHashValue(header.getHashOfPreviousBlock(), nonce, messageDigest);

            if (hashValue.compareTo(header.getDifficulty()) < 0) {
                endTime = System.currentTimeMillis();
                header.setValidNonce(nonce);
                header.setUnixTime(System.currentTimeMillis() / 1000L);

                System.out.println("Valid nonce is: " + nonce + ", Number of nonces checked: " + numberOfNoncesChecked);
                System.out.println("Hash Value is: " + hashValue);

                break;
            }
        }
        System.out.println("Time to find a valid hash: " + getTimeElapsedInSeconds(startTime, endTime) + " seconds");
    }

    private static Double getTimeElapsedInSeconds(long startTime, long endTime) {
        return (endTime - startTime) / 1000.;
    }

    private static String getNonce(Random rand) {
        return Integer.toHexString(rand.nextInt());
    }

    private static String getHashValue(String hashOfPreviousBlock, String nonce, MessageDigest messageDigest) throws UnsupportedEncodingException {
        messageDigest.update((hashOfPreviousBlock + nonce).getBytes(UTF8));
        byte[] digestBytes = messageDigest.digest();
        return DatatypeConverter.printHexBinary(digestBytes).toLowerCase();
    }
}
