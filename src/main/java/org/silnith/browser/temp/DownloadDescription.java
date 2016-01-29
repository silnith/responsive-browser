package org.silnith.browser.temp;

import java.net.URL;

public class DownloadDescription {

    private final URL url;

    public DownloadDescription(final URL url) {
        super();
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof DownloadDescription) {
            final DownloadDescription other = (DownloadDescription) obj;
            return url.equals(other.url);
        } else {
            return false;
        }
    }

}
