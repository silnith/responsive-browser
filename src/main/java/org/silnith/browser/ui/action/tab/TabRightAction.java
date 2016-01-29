package org.silnith.browser.ui.action.tab;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;

public class TabRightAction extends AbstractAction {

    private final JTabbedPane tabbedPane;

    public TabRightAction(final JTabbedPane tabbedPane) {
        super("Set Tab Right");
        this.tabbedPane = tabbedPane;
        
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_R);
        this.putValue(SHORT_DESCRIPTION, "Set Tab Right");
        this.putValue(LONG_DESCRIPTION, "Display the tabs on the right side of the pane.");
        this.putValue(SELECTED_KEY, this.tabbedPane.getTabPlacement() == JTabbedPane.RIGHT);
    }

    @Override
    public void actionPerformed(final ActionEvent arg0) {
        assert EventQueue.isDispatchThread();
        
        tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
    }

}
