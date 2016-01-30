package org.silnith.browser.network;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import org.silnith.browser.model.PreferenceHolder;


public class FileCacheManager implements CacheManager, PreferenceHolder {
    
    private static final String CACHE_DIRECTORY = "cache.dir";
    
    private static final Preferences USER_PREFERENCES = Preferences.userNodeForPackage(FileCacheManager.class);
    
    @Override
    public CacheEntry getCacheEntry(final URL url) {
        final File cacheDirectoryFile = getCacheDirectory();
        
        final Path cacheDirectoryPath = cacheDirectoryFile.toPath();
        
        try {
            final Path created = Files.createDirectories(cacheDirectoryPath);
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        cacheDirectoryPath.resolve(convertProtocolToRelativePath(url.getProtocol()));
        url.getHost();
        url.getPort();
        url.getDefaultPort();
        url.getPath();
        url.getQuery();
        
        return null;
    }
    
    public CacheEntry getCacheEntry(final URI uri) {
        uri.getScheme();
        uri.getHost();
        uri.getPort();
        uri.getRawPath();
        uri.getRawQuery();
        uri.getRawAuthority();
        uri.getRawUserInfo();
        
        uri.getRawSchemeSpecificPart();
        return null;
    }
    
    private String getUserHomeDirectory() {
        final String userHomeString = System.getProperty("user.home");
        
        assert userHomeString != null;
        
        return userHomeString;
    }
    
    private File getCacheDirectory() {
        final String cacheDirectoryString = USER_PREFERENCES.get(CACHE_DIRECTORY, getUserHomeDirectory());
        final Path cacheDirectoryPath = Paths.get(cacheDirectoryString);
        final File cacheDirectoryFile = new File(cacheDirectoryString);
        final boolean created = cacheDirectoryFile.mkdirs();
        if (created) {
            System.out.print("Created cache directory: ");
            System.out.println(cacheDirectoryFile);
        }
        return cacheDirectoryFile;
    }
    
    private Path convertProtocolToRelativePath(final String protocol) {
        return Paths.get("protocol_" + protocol);
    }
    
    public static void main(final String[] args) throws URISyntaxException, MalformedURLException {
        final URL url = new URL("https://roske001:foo@192.168.0.4/~roske001/image/resource [1].gif");
        final URI uri = url.toURI();
        System.out.println(uri);
        System.out.println(uri.toASCIIString());
        System.out.println(uri.toURL());
        
        System.out.println(uri.getPath());
        System.out.println(uri.getRawPath());
    }
    
}
