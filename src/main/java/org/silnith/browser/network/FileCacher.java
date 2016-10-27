package org.silnith.browser.network;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * A semi-trivial program that downloads a network resource and saves it in a file.
 */
public class FileCacher {
    
    public static void main(String[] args) throws IOException {
        final RandomAccessFile randomAccessFile = new RandomAccessFile(new File("galtse.cx.txt"), "rw");
        
        final FileSystem defaultFileSystem = FileSystems.getDefault();
        final Path galtPath = defaultFileSystem.getPath("galt.txt");
        
        final URI uri = URI.create("http://galtse.cx/");
        final URL url = uri.toURL();
        final URLConnection connection = url.openConnection();
        connection.connect();
        try (final InputStream inputStream = connection.getInputStream()) {
            final long contentLengthLong = connection.getContentLengthLong();
            randomAccessFile.setLength(contentLengthLong);
            randomAccessFile.seek(0);

            Files.copy(inputStream, galtPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
}
