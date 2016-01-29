package org.silnith.browser.ui.action;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.silnith.browser.ui.BrowserWindow;

public class NewTabAction extends AbstractAction {

    private final BrowserWindow browserWindow;

    public NewTabAction(final BrowserWindow browserWindow) {
        super("New Tab");
        this.browserWindow = browserWindow;
        
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_T);
        this.putValue(SHORT_DESCRIPTION, "Open a new tab.");
        this.putValue(LONG_DESCRIPTION, "Opens a new tab in the current window.");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        assert EventQueue.isDispatchThread();
        
        browserWindow.createNewTab();
    }

}
