//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;


public class BloomFilter {
    private final BitSet bitset;
    private final int numHashFunctions;
    private final HashFunction[] hashFunctions;

    public BloomFilter(int bitsetSize, String... algNames)  {
        this.bitset = new BitSet(bitsetSize);
        this.numHashFunctions = algNames.length;
        this.hashFunctions = new HashFunction[numHashFunctions];

        for (int i = 0; i < numHashFunctions; i++) {
            try {
                hashFunctions[i] = new HashFunction(algNames[i]);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void add(String word) {
        for (HashFunction hashFunction : hashFunctions) {
            int index = hashFunction.hash(word) % bitset.size();
            bitset.set(index);
        }
    }

    public boolean contains(String word) {
        for (HashFunction hashFunction : hashFunctions) {
            int index = hashFunction.hash(word) % bitset.size();
            if (!bitset.get(index)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitset.length() ; i++) {
            sb.append(bitset.get(i) ? '1' : '0');
        }
        return sb.toString();
    }

    private class HashFunction {

        private final MessageDigest digest;

        public HashFunction(String algorithm) throws NoSuchAlgorithmException {
            this.digest = MessageDigest.getInstance(algorithm);
        }

        public int hash(String word) {
            byte[] bytes = word.getBytes();
            digest.update(bytes);
            byte[] hashBytes = digest.digest();
            BigInteger hashInt = new BigInteger(1, hashBytes);
            return Math.abs(hashInt.intValue()) % bitset.size();
        }
    }
}
