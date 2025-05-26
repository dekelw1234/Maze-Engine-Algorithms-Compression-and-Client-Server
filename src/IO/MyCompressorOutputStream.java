package IO;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Compressor stream using LZW with 24-bit codes.
 * First 12 bytes (metadata) are written unchanged, then the rest are LZW-compressed.
 */
public class MyCompressorOutputStream extends FilterOutputStream {
    private static final int DICT_INIT_SIZE = 256;

    public MyCompressorOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (b == null || b.length <= 12) {
            out.write(b);
            return;
        }

        // 1) כתיבת 12 הבייטים הראשונים ללא שינוי
        out.write(b, 0, 12);

        // 2) הקמת מילון התחלתי: כל בייט יחיד
        Map<List<Byte>, Integer> dict = new HashMap<>();
        for (int i = 0; i < DICT_INIT_SIZE; i++) {
            dict.put(Collections.singletonList((byte) i), i);
        }
        int dictSize = DICT_INIT_SIZE;

        // 3) לולאת LZW על שאר המידע
        List<Byte> w = new ArrayList<>();
        for (int i = 12; i < b.length; i++) {
            byte k = b[i];
            List<Byte> wk = new ArrayList<>(w);
            wk.add(k);
            if (dict.containsKey(wk)) {
                w = wk;
            } else {
                // כותבים קוד עבור w
                writeCode(dict.get(w));
                // מוסיפים למילון
                dict.put(wk, dictSize++);
                // מתחילים רצף חדש עם k
                w = new ArrayList<>();
                w.add(k);
            }
        }
        // כותבים קוד עבור הרצף האחרון
        if (!w.isEmpty()) {
            writeCode(dict.get(w));
        }

        out.flush();
    }

    /** כותב קוד 24-bit (3 בייטים) big-endian */
    private void writeCode(int code) throws IOException {
        out.write((code >>> 16) & 0xFF);
        out.write((code >>> 8)  & 0xFF);
        out.write(code & 0xFF);
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
