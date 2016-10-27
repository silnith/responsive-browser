package org.silnith.browser.model.url;

import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;


public class URLDocument extends AbstractDocument implements Document {
    
    public URLDocument() {
        super(new URLContent());
    }
    
    @Override
    public Element getDefaultRootElement() {
        final AbstractDocument.BranchElement uriElement = new AbstractDocument.BranchElement(null, null);
        ;
        return null;
    }
    
    @Override
    public Element getParagraphElement(int pos) {
        return null;
    }
    
}
