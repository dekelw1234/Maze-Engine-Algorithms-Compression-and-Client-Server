package IO;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Decompressor stream for the above LZW-24 bit format.
 * Assumes: 12 bytes metadata + sequence of 24-bit codes.
 */
public class MyDecompressorInputStream extends FilterInputStream {
    private static final int DICT_INIT_SIZE = 256;

    public MyDecompressorInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (b == null || b.length <= 12) {
            return in.read(b);
        }

        // 1) קריאת 12 בייטי המטא־דאטה
        int totalRead = 0;
        while (totalRead < 12) {
            int v = in.read();
            if (v < 0) return -1;
            b[totalRead++] = (byte) v;
        }

        // 2) הקמת מילון התחלתי: קוד→[single-byte]
        Map<Integer, List<Byte>> dict = new HashMap<>();
        for (int i = 0; i < DICT_INIT_SIZE; i++) {
            dict.put(i, Collections.singletonList((byte) i));
        }
        int dictSize = DICT_INIT_SIZE;

        // 3) קריאה של הקוד הראשון (24-bit)
        int prevCode = readCode();
        if (prevCode < 0) return totalRead;
        List<Byte> entry = new ArrayList<>(dict.get(prevCode));
        for (byte bb : entry) {
            if (totalRead < b.length) b[totalRead++] = bb;
        }

        // 4) לולאה עיקרית: קריאה עד מילוי b
        while (totalRead < b.length) {
            int currCode = readCode();
            if (currCode < 0) break;

            List<Byte> currEntry;
            if (dict.containsKey(currCode)) {
                currEntry = dict.get(currCode);
            } else if (currCode == dictSize) {
                // מקרה מיוחד: prevSequence + firstByte(prevSequence)
                currEntry = new ArrayList<>(entry);
                currEntry.add(entry.get(0));
            } else {
                throw new IOException("Bad LZW code: " + currCode);
            }

            // כותבים את currEntry ל־b
            for (byte bb : currEntry) {
                if (totalRead < b.length) b[totalRead++] = bb;
            }

            // מוסיפים למילון: prevSequence + firstByte(currEntry)
            List<Byte> newSeq = new ArrayList<>(entry);
            newSeq.add(currEntry.get(0));
            dict.put(dictSize++, newSeq);

            entry = currEntry;
        }

        return totalRead;
    }

    /** קורא קוד 24-bit (3 בייטים) big-endian */
    private int readCode() throws IOException {
        int hi  = in.read();
        if (hi < 0) return -1;
        int mid = in.read();
        if (mid < 0) return -1;
        int lo  = in.read();
        if (lo < 0) return -1;
        return (hi << 16) | (mid << 8) | lo;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
