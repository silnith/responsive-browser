package org.silnith.browser.ui.action.tab;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;


public class TabWrapAction extends AbstractAction {
    
    private final JTabbedPane tabbedPane;
    
    public TabWrapAction(final JTabbedPane tabbedPane) {
        super("Wrap Overflow Tabs");
        this.tabbedPane = tabbedPane;
        
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_W);
        this.putValue(SHORT_DESCRIPTION, "Wrap Overflow Tabs");
        this.putValue(LONG_DESCRIPTION, "Display overflow tabs by wrapping.");
        this.putValue(SELECTED_KEY, this.tabbedPane.getTabLayoutPolicy() == JTabbedPane.WRAP_TAB_LAYOUT);
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        assert EventQueue.isDispatchThread();
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    }
    
}
