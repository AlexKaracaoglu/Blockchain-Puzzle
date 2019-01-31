/*
 * Blockchain Puzzle: BlockchainHeader.java
 * Homework 2 - Problem 2
 * Group 9
 */

import java.util.Random;

public class BlockchainHeader {

    private String hashOfPreviousBlock;

    private String merkleRoot;

    private Long unixTime;

    private String difficulty;

    private String validNonce;

    public BlockchainHeader(String difficulty) {
        Random rand = new Random();
        int random1 = rand.nextInt();
        int random2 = rand.nextInt();
        this.hashOfPreviousBlock = Integer.toHexString(random1);
        this.merkleRoot = Integer.toHexString(random2);
        this.difficulty = difficulty;
    }

    public String getHashOfPreviousBlock() {
        return hashOfPreviousBlock;
    }

    public void setHashOfPreviousBlock(String hashOfPreviousBlock) {
        this.hashOfPreviousBlock = hashOfPreviousBlock;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public Long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(Long unixTime) {
        this.unixTime = unixTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getValidNonce() {
        return validNonce;
    }

    public void setValidNonce(String validNonce) {
        this.validNonce = validNonce;
    }
}
