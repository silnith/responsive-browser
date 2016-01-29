package org.silnith.browser.model;

import java.net.URL;


public class NavigationRequest {

    private final URL url;

    public NavigationRequest(final URL url) {
        super();
        this.url = url;
    }

    public URL getURL() {
        return url;
    }

}
