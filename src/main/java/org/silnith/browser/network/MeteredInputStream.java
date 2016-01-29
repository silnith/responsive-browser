package org.silnith.browser.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;


public class MeteredInputStream extends InputStream {

    private final InputStream wrappedInputStream;

    private final AtomicLong numBytesRead;

    public MeteredInputStream(final InputStream wrappedInputStream) {
        super();
        this.wrappedInputStream = wrappedInputStream;
        this.numBytesRead = new AtomicLong();
    }

    @Override
    public int read() throws IOException {
        final int byteRead = this.wrappedInputStream.read();
        if (byteRead != -1) {
            this.numBytesRead.incrementAndGet();
        }
        return byteRead;
    }

    @Override
    public int read(final byte[] b) throws IOException {
        final int bytesRead = this.wrappedInputStream.read(b);
        if (bytesRead > 0) {
            this.numBytesRead.addAndGet(bytesRead);
        }
        return bytesRead;
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        final int bytesRead = this.wrappedInputStream.read(b, off, len);
        if (bytesRead > 0) {
            this.numBytesRead.addAndGet(bytesRead);
        }
        return bytesRead;
    }

    @Override
    public long skip(final long n) throws IOException {
        final long bytesSkipped = this.wrappedInputStream.skip(n);
        if (bytesSkipped > 0) {
            this.numBytesRead.addAndGet(bytesSkipped);
        }
        return bytesSkipped;
    }

    @Override
    public int available() throws IOException {
        return this.wrappedInputStream.available();
    }

    @Override
    public void close() throws IOException {
        this.wrappedInputStream.close();
    }

    public long getBytesRead() {
        return this.numBytesRead.get();
    }

}
