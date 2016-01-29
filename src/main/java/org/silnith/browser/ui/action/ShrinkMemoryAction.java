package org.silnith.browser.ui.action;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;


public class ShrinkMemoryAction extends AbstractAction {

    public ShrinkMemoryAction() {
        super("Shrink Memory");
        
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_M);
        this.putValue(SHORT_DESCRIPTION, "Shrink Memory");
        this.putValue(LONG_DESCRIPTION, "Shrink the amount of memory currently used.");
    }

    @Override
    public void actionPerformed(final ActionEvent arg0) {
        assert EventQueue.isDispatchThread();
        
        final SwingWorker<String, String> worker = new SwingWorker<String, String>() {
            
            @Override
            protected String doInBackground() throws Exception {
                assert !EventQueue.isDispatchThread();
                
                System.gc();
                
                return null;
            }
            
        };
        
        worker.execute();
    }

}
