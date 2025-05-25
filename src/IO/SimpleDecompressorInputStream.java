package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * A decompressor that reads the first 12 bytes (metadata) unchanged,
 * then decodes RLE data in [length][value] order back into the original bytes.
 */
public class SimpleDecompressorInputStream extends InputStream {
    private final InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (b == null || b.length == 0) return -1;

        // 1) read the first 12 bytes (metadata)
        for (int i = 0; i < 12; i++) {
            int v = in.read();
            if (v == -1) return -1;
            b[i] = (byte) v;
        }

        // 2) decode RLE [length][value]
        int index = 12;
        while (index < b.length) {
            int length = in.read();
            if (length == -1) break;
            int value = in.read();
            if (value == -1) break;

            for (int i = 0; i < length && index < b.length; i++) {
                b[index++] = (byte) value;
            }
        }

        return index;  // number of bytes written into `b`
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
