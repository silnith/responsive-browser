package org.silnith.browser.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;


public class DownloadTask implements Callable<Reader> {
    
    private final URL url;
    
    private final int bufferSize;
    
    public DownloadTask(final URL url, final int bufferSize) {
        super();
        if (url == null) {
            throw new IllegalArgumentException();
        }
        if (bufferSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.url = url;
        this.bufferSize = bufferSize;
    }
    
    @Override
    public Reader call() throws Exception {
        final URLConnection connection = url.openConnection();
        final long contentLengthLong = connection.getContentLengthLong();
        final String contentType = connection.getContentType();
        final String contentEncoding = connection.getContentEncoding();
        
        final byte[] byteArray;
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try (final InputStream inputStream = connection.getInputStream()) {
                final byte[] buffer = new byte[bufferSize];
                int read = inputStream.read(buffer);
                while (read != -1) {
                    outputStream.write(buffer, 0, read);
                    
                    read = inputStream.read(buffer);
                }
            }
            byteArray = outputStream.toByteArray();
        }
        if (contentEncoding == null) {
            return new InputStreamReader(new ByteArrayInputStream(byteArray), Charset.forName("UTF-8"));
        } else {
            return new InputStreamReader(new ByteArrayInputStream(byteArray), contentEncoding);
        }
    }
    
}
