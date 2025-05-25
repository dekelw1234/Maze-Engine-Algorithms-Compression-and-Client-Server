package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private final InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (b == null || b.length == 0) return -1;

        for (int i = 0; i < 12; i++) {
            b[i] = (byte) in.read();
        }

        int index = 12;
        while (index < b.length) {
            int value = in.read();
            int count = in.read();

            for (int i = 0; i < count && index < b.length; i++) {
                b[index++] = (byte) value;
            }
        }

        return index;
    }
}