package org.silnith.browser.ui.action;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import org.silnith.browser.ResponsiveBrowser;


public class ExitBrowserAction extends AbstractAction {
    
    private final ResponsiveBrowser application;
    
    public ExitBrowserAction(final ResponsiveBrowser application) {
        super("Exit Browser");
        this.application = application;
        
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        this.putValue(SHORT_DESCRIPTION, "Exit the browser.");
        this.putValue(LONG_DESCRIPTION, "Close all windows and exit the browser.");
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        assert EventQueue.isDispatchThread();
        
        ;
    }
    
}
