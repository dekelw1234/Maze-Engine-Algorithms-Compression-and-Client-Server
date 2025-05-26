package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A compressor that writes the first 12 bytes (metadata) unchanged,
 * then encodes the rest of the bytes using RLE in [length][value] order.
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private final OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        // delegate single-byte writes (metadata or raw) directly
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (b == null || b.length == 0) return;

        // 1) write the first 12 bytes (metadata) as-is
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        // 2) compress the remaining bytes with RLE [length][value]
        byte curr = b[12];
        int count = 1;
        for (int i = 13; i < b.length; i++) {
            if (b[i] == curr && count < 255) {
                count++;
            } else {
                writeRun(count, curr);
                curr = b[i];
                count = 1;
            }
        }
        // write the final run
        writeRun(count, curr);

        out.flush();
    }

    private void writeRun(int length, byte value) throws IOException {
        // if run is longer than 255, split it
        while (length > 255) {
            out.write(255);
            out.write(value);
            length -= 255;
        }
        out.write(length);
        out.write(value);
    }

    @Override
    public void close() throws IOException {
        out.flush();
        out.close();
    }
}
