package org.silnith.browser.model.url;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;


/**
 * Represents the fragment portion of an URI.
 * 
 * @author Kent Rosenkoetter
 * @see <a href="https://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>
 */
public class FragmentElement implements Element {
    
    @Override
    public Document getDocument() {
        return null;
    }
    
    @Override
    public Element getParentElement() {
        return null;
    }
    
    @Override
    public String getName() {
        return "fragment";
    }
    
    @Override
    public AttributeSet getAttributes() {
        return null;
    }
    
    @Override
    public int getStartOffset() {
        return 0;
    }
    
    @Override
    public int getEndOffset() {
        return 0;
    }
    
    @Override
    public int getElementIndex(int offset) {
        return 0;
    }
    
    @Override
    public int getElementCount() {
        return 0;
    }
    
    @Override
    public Element getElement(int index) {
        return null;
    }
    
    @Override
    public boolean isLeaf() {
        return false;
    }
    
}
