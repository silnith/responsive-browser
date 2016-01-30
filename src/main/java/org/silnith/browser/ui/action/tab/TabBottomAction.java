package org.silnith.browser.ui.action.tab;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;


public class TabBottomAction extends AbstractAction {
    
    private final JTabbedPane tabbedPane;
    
    public TabBottomAction(final JTabbedPane tabbedPane) {
        super("Set Tab Bottom");
        this.tabbedPane = tabbedPane;
        
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_B);
        this.putValue(SHORT_DESCRIPTION, "Set Tab Bottom");
        this.putValue(LONG_DESCRIPTION, "Display the tabs on the bottom of the pane.");
        this.putValue(SELECTED_KEY, this.tabbedPane.getTabPlacement() == SwingConstants.BOTTOM);
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        assert EventQueue.isDispatchThread();
        
        tabbedPane.setTabPlacement(SwingConstants.BOTTOM);
    }
    
}
