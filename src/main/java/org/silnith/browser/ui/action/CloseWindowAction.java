package org.silnith.browser.ui.action;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.silnith.browser.ui.BrowserWindow;

public class CloseWindowAction extends AbstractAction {

    private final BrowserWindow browserWindow;

    public CloseWindowAction(final BrowserWindow browserWindow) {
        super("Close Window");
        this.browserWindow = browserWindow;
        
//        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        this.putValue(SHORT_DESCRIPTION, "Close Window");
        this.putValue(LONG_DESCRIPTION, "Close the currently open window and all its tabs.");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        assert EventQueue.isDispatchThread();
        
        final int numberOfTabs = browserWindow.getNumberOfTabs();
        if (numberOfTabs < 2) {
            browserWindow.closeWindow();
        } else {
            final String message = String.format("The window currently has %d tabs open.  Close all tabs?", numberOfTabs);
            
            final int choice = JOptionPane.showConfirmDialog(browserWindow,
                    message,
                    "Close Window?",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            switch (choice) {
            case JOptionPane.OK_OPTION: {
                browserWindow.closeWindow();
            } break;
            case JOptionPane.CANCEL_OPTION: {
                // do nothing
            } break;
            default: {} break;
            }
        }
    }

}
