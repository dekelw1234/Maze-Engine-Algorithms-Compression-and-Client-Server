package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private final OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (b == null || b.length == 0) return;

        // Write metadata (first 12 bytes) as-is
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        // Compress the remaining bytes using RLE (Run-Length Encoding)
        byte curr = b[12];
        int count = 1;

        for (int i = 13; i < b.length; i++) {
            if (b[i] == curr) {
                count++;
            } else {
                writeCompressedByte(curr, count);
                curr = b[i];
                count = 1;
            }
        }
        writeCompressedByte(curr, count);
    }

    private void writeCompressedByte(byte value, int count) throws IOException {
        while (count > 255) {
            out.write(value);
            out.write(255);
            count -= 255;
        }
        out.write(value);
        out.write(count);
    }
}
