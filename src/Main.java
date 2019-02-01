/*
 * Blockchain Puzzle: Main.java
 * Homework 2 - Problem 2
 * Group 9
 */


/*
 * RESOURCES USED:
 *
 * https://www.baeldung.com/java-byte-arrays-hex-strings (Message Digest Usage for SHA-256)
 * https://stackoverflow.com/questions/732034/getting-unixtime-in-java (Getting unix time)
 * https://stackoverflow.com/questions/4400774/java-calculate-hex-representation-of-a-sha-1-digest-of-a-string  (Message Digest General Usage)
 * https://stackoverflow.com/questions/5317320/regex-to-check-string-contains-only-hex-characters (Checking Hex Format of Difficulty)
 * https://en.bitcoin.it/wiki/Block_hashing_algorithm (Resource on General Idea)
 *
 */

/*
 * Some things to note:
 *      1. difficulty = 64 length hex representation = 256 bits
 *      2. nonce = Long (we start at 0 and increment)
 *          When format hash of block header, we change to 32 bit hex string with %08x
 *      3. unixTime = Long
 *          When format hash of block header, we change to 32 bit hex string with %08x
 */

// TODO: difficulty - We should see if there's a better way to work with this

// TODO: unixTime - Do we need to update every iteration or just set it at beginning and let run until nonce found?

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

    private static final String UTF8 = "utf8";
    private static final String SHA_256 = "SHA-256";
    private static final Integer SIXTY_FOUR = 64;
    private static final String HEX_FORMAT = "[0-9a-f]+";
    private static final String EIGHT_HEX_DIGITS = "%08x";

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String difficulty = "f000004011111111111111111111111111111111111111111111111111111111";
        checkValidDifficultyFormat(difficulty);

        Long startTime = System.currentTimeMillis();
        Long endTime = -1L;

        Long nonce = 0L;

        MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
        BlockchainHeader header = new BlockchainHeader(difficulty);

        while (Boolean.TRUE) {
            String hashValue = updateHeaderAndHashContents(nonce, header, messageDigest);

            if (hashValue.compareTo(header.getDifficulty()) < 0) {
                endTime = System.currentTimeMillis();

                System.out.println("Valid nonce is: " + nonce + ", Number of nonces checked: " + (nonce + 1));
                System.out.println("Hash Value is: " + hashValue);

                break;
            }
            nonce++;
        }
        System.out.println("Time to find a valid hash: " + getTimeElapsedInSeconds(startTime, endTime) + " seconds");
    }

    private static String updateHeaderAndHashContents(long nonce, BlockchainHeader header, MessageDigest messageDigest) throws UnsupportedEncodingException {
        header.setNonce(nonce);
        header.setUnixTime();

        String headerString = getHeaderString(header);

        messageDigest.update(headerString.getBytes(UTF8));
        byte[] digestBytes = messageDigest.digest();
        return DatatypeConverter.printHexBinary(digestBytes).toLowerCase();
    }

    private static String getHeaderString(BlockchainHeader header) {
        return header.getHashOfPreviousBlockHeader() +
                header.getMerkleRoot() +
                String.format(EIGHT_HEX_DIGITS, header.getUnixTime()) +
                header.getDifficulty() +
                String.format(EIGHT_HEX_DIGITS, header.getNonce());
    }

    private static void checkValidDifficultyFormat(String difficulty) {
        if (difficulty.length() != SIXTY_FOUR) {
            throw new IllegalArgumentException("Difficulty hex string must be of length 64");
        }
        if (!difficulty.matches(HEX_FORMAT)) {
            throw new IllegalArgumentException("Difficulty string must include only hex digits");
        }
    }

    private static Double getTimeElapsedInSeconds(long startTime, long endTime) {
        return (endTime - startTime) / 1000.;
    }
}
