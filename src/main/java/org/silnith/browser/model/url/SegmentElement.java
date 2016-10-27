package org.silnith.browser.model.url;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;


public class SegmentElement implements Element {
    
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
        return null;
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
