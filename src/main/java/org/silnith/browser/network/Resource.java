package org.silnith.browser.network;

import java.nio.charset.Charset;


public class Resource {
    
    private final byte[] content;
    
    private final String contentType;
    
    private final Charset encoding;
    
    public Resource(final byte[] content, final String contentType, final Charset encoding) {
        super();
        this.content = content;
        this.contentType = contentType;
        this.encoding = encoding;
    }
    
}
