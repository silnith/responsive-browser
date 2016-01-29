package org.silnith.browser.ui.action.tab;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;

public class TabTopAction extends AbstractAction {

    private final JTabbedPane tabbedPane;

    public TabTopAction(final JTabbedPane tabbedPane) {
        super("Set Tab Top");
        this.tabbedPane = tabbedPane;
        
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_T);
        this.putValue(SHORT_DESCRIPTION, "Set Tab Top");
        this.putValue(LONG_DESCRIPTION, "Display the tabs on the top of the pane.");
        this.putValue(SELECTED_KEY, this.tabbedPane.getTabPlacement() == JTabbedPane.TOP);
    }

    @Override
    public void actionPerformed(final ActionEvent arg0) {
        assert EventQueue.isDispatchThread();
        
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
    }

}
