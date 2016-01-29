package org.silnith.browser.temp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class CacheManager {

    private final ConcurrentMap<DownloadDescription, Object> currentDownloads;

    public CacheManager() {
        super();
        this.currentDownloads = new ConcurrentHashMap<>();
    }

    public Object offerDownload(final DownloadOffer downloadOffer) throws IOException {
        final FileSystem defaultFileSystem = FileSystems.getDefault();
        
        final String userHomeString = System.getProperty("user.home");
        final Path userHomePath = defaultFileSystem.getPath(userHomeString);
        final Path cachePath = userHomePath.resolve(Paths.get(".responsiveBrowser", "cache"));
        
        System.out.println(userHomePath);
        System.out.println(userHomePath.toUri());
        System.out.println(cachePath);
        System.out.println(cachePath.toUri());
        
        final URL url = downloadOffer.getDownloadDescription().getUrl();
        
//        System.out.println();
//        System.out.println(url);
//        System.out.println(url.getProtocol());
//        System.out.println(url.getHost());
//        System.out.println(url.getPort());
//        System.out.println(url.getDefaultPort());
        System.out.println(url.getPath());
//        System.out.println(url.getQuery());
//        System.out.println(url.getRef());
//        System.out.println(Arrays.toString(url.getPath().split(Pattern.quote("/"))));
//        if (1 == 1) {
//            return null;
//        }
        
        final Path cacheEntryPath = cachePath
                .resolve(getPathForProtocol(url.getProtocol()))
                .resolve(getPathForHost(url.getHost()))
                .resolve(getPathForPort(url.getPort(), url.getDefaultPort()))
                .resolve(getPathForPath(url.getPath()));
//        final Path hostCachePath = cachePath.resolve(Paths.get(url.getProtocol(), url.getHost()));
//        final Path cacheEntryPath = hostCachePath.resolve(Paths.get(url.getPath().substring(1)));
        
        System.out.println(cacheEntryPath);
        System.out.println(cacheEntryPath.toUri());
        
        Files.createDirectories(cacheEntryPath.getParent());
        
        final Set<StandardOpenOption> options = EnumSet.noneOf(StandardOpenOption.class);
        options.add(StandardOpenOption.READ);
        options.add(StandardOpenOption.WRITE);
        options.add(StandardOpenOption.CREATE);
        final FileChannel cacheChannel = FileChannel.open(cacheEntryPath, options.toArray(new OpenOption[0]));
//        final FileChannel cacheChannel = FileChannel.open(cacheEntryPath, options, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("r--r--rw-")));
        
        System.out.println(cacheChannel.size());
        
        final URLConnection openConnection = url.openConnection();
        final int contentLength = openConnection.getContentLength();
        
        System.out.println(contentLength);
        
        if (contentLength != -1) {
            try (final InputStream inputStream = openConnection.getInputStream()) {
                final long transferred = cacheChannel.transferFrom(Channels.newChannel(inputStream), 0, contentLength);
                
                System.out.println(transferred);
            }
        } else {
            try (final InputStream inputStream = openConnection.getInputStream()) {
                final byte[] buffer = new byte[8192];
                final ByteBuffer wrapped = ByteBuffer.wrap(buffer);
                int read = inputStream.read(buffer);
                while (read != -1) {
                    System.out.println(read);
                    cacheChannel.write(wrapped);
                    read = inputStream.read(buffer);
                }
            }
        }
        
//        final Object existingDownload = currentDownloads.putIfAbsent(downloadOffer.getDownloadDescription(), null);
        
        return null;
    }

    private Path getPathForProtocol(final String protocol) {
        return Paths.get("protocol_" + protocol);
    }

    private Path getPathForHost(final String host) {
        return Paths.get("host_" + host);
    }

    private Path getPathForPort(final int port, final int defaultPort) {
        if (port == -1) {
            return Paths.get("port_" + defaultPort);
        } else {
            return Paths.get("port_" + port);
        }
    }

    private Path getPathForPath(final String path) {
        if (path == null) {
            return Paths.get("file_");
        } else {
            final int lastIndexOf = path.lastIndexOf('/');
            if (lastIndexOf == -1) {
                return Paths.get("file_" + path);
            } else {
                final String dirs = path.substring(0, lastIndexOf);
                final String file = path.substring(lastIndexOf + 1);
                
                if (dirs.isEmpty()) {
                    return Paths.get("file_" + file);
                } else {
                    assert dirs.startsWith("/");
                    
                    Path dirPath = Paths.get("");
                    final String[] split = dirs.substring(1).split(Pattern.quote("/"));
                    for (final String element : split) {
                        dirPath = dirPath.resolve(Paths.get("path_" + element));
                    }
                    
                    return dirPath.resolve(Paths.get("file_" + file));
                }
            }
        }
    }

    public static void main(final String[] args) throws IOException {
        final CacheManager cacheManager = new CacheManager();
        
        cacheManager.offerDownload(new DownloadOffer(new DownloadDescription(new URL("http://www.w3.org/TR/html5/infrastructure.html#content-type-sniffing"))));
//        cacheManager.offerDownload(new DownloadOffer(new DownloadDescription(new URL("http://www.w3.org/foo"))));
        cacheManager.offerDownload(new DownloadOffer(new DownloadDescription(new URL("http://www.w3.org/"))));
        cacheManager.offerDownload(new DownloadOffer(new DownloadDescription(new URL("http://www.w3.org"))));
    }

}
