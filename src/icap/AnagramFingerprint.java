package icap;

import java.util.Arrays;

public class AnagramFingerprint {

    private int[] fingerprint = new int[26];

    AnagramFingerprint(String word) {
        char[] cs = word.toLowerCase().toCharArray();
        for (char c : cs) {
            int index = getIndex(c);
            fingerprint[index]++;
        }
    }

    private int getIndex(char c) {
        return c == 'a' ? 0 : c - 'a';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnagramFingerprint that = (AnagramFingerprint) o;
        return Arrays.equals(fingerprint, that.fingerprint);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(fingerprint);
    }
}
