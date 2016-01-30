package org.silnith.browser.ui.action.tab;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;


public class TabLeftAction extends AbstractAction {
    
    private final JTabbedPane tabbedPane;
    
    public TabLeftAction(final JTabbedPane tabbedPane) {
        super("Set Tab Left");
        this.tabbedPane = tabbedPane;
        
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_L);
        this.putValue(SHORT_DESCRIPTION, "Set Tab Left");
        this.putValue(LONG_DESCRIPTION, "Display the tabs on the left side of the pane.");
        this.putValue(SELECTED_KEY, this.tabbedPane.getTabPlacement() == SwingConstants.LEFT);
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        assert EventQueue.isDispatchThread();
        
        tabbedPane.setTabPlacement(SwingConstants.LEFT);
    }
    
}
