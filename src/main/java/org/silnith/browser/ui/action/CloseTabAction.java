package org.silnith.browser.ui.action;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.silnith.browser.ui.BrowserWindow;


public class CloseTabAction extends AbstractAction {
    
    private final BrowserWindow browserWindow;
    
    public CloseTabAction(final BrowserWindow browserWindow) {
        super("Close Tab");
        this.browserWindow = browserWindow;
        
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        this.putValue(SHORT_DESCRIPTION, "Close the current tab.");
        this.putValue(LONG_DESCRIPTION, "Closes the current tab in the current window.");
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        assert EventQueue.isDispatchThread();
        
        browserWindow.closeCurrentTab();
    }
    
}
