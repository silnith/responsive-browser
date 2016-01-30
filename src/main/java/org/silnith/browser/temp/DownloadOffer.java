package org.silnith.browser.temp;

import java.io.IOException;
import java.io.InputStream;


public class DownloadOffer {
    
    private final DownloadDescription downloadDescription;
    
    public DownloadOffer(final DownloadDescription downloadDescription) {
        super();
        this.downloadDescription = downloadDescription;
    }
    
    public DownloadDescription getDownloadDescription() {
        return downloadDescription;
    }
    
    public InputStream getInputStream() throws IOException {
        return downloadDescription.getUrl().openStream();
    }
    
}
