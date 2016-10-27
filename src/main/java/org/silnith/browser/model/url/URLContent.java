package org.silnith.browser.model.url;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.undo.UndoableEdit;


public class URLContent implements AbstractDocument.Content {
    
    private String scheme;
    private String schemeSpecificPart;
    private String host;
    private String port;
    private String query;
    private String fragment;
    
    @Override
    public Position createPosition(int offset) throws BadLocationException {
        return null;
    }
    
    @Override
    public int length() {
        return 0;
    }
    
    @Override
    public UndoableEdit insertString(int where, String str) throws BadLocationException {
        return null;
    }
    
    @Override
    public UndoableEdit remove(int where, int nitems) throws BadLocationException {
        return null;
    }
    
    @Override
    public String getString(int where, int len) throws BadLocationException {
        return null;
    }
    
    @Override
    public void getChars(int where, int len, Segment txt) throws BadLocationException {
    }
    
}
