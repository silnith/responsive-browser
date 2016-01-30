package org.silnith.browser.model;

import java.net.URL;


public class DownloadRequest {
    
    private final URL url;
    
    public DownloadRequest(final URL url) {
        super();
        this.url = url;
    }
    
    public URL getUrl() {
        return url;
    }
    
}
