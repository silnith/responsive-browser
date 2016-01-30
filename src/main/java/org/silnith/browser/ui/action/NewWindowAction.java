package org.silnith.browser.ui.action;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.silnith.browser.ResponsiveBrowser;


public class NewWindowAction extends AbstractAction {
    
    private final ResponsiveBrowser application;
    
    public NewWindowAction(final ResponsiveBrowser application) {
        super("New Window");
        this.application = application;
        
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        this.putValue(SHORT_DESCRIPTION, "Opens a new window.");
        this.putValue(LONG_DESCRIPTION, "Opens a new window with a single tab in it.");
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        assert EventQueue.isDispatchThread();
        
        application.createNewWindow();
    }
    
}
