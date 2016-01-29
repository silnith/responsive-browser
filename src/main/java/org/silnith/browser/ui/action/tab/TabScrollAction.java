package org.silnith.browser.ui.action.tab;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;

public class TabScrollAction extends AbstractAction {

    private final JTabbedPane tabbedPane;

    public TabScrollAction(final JTabbedPane tabbedPane) {
        super("Scroll Overflow Tabs");
        this.tabbedPane = tabbedPane;
        
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        this.putValue(SHORT_DESCRIPTION, "Scroll Overflow Tabs");
        this.putValue(LONG_DESCRIPTION, "Display overflow tabs by scrolling.");
        this.putValue(SELECTED_KEY, this.tabbedPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    @Override
    public void actionPerformed(final ActionEvent arg0) {
        assert EventQueue.isDispatchThread();
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

}
