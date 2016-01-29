package org.silnith.browser.ui.action;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SetLookAndFeelAction extends AbstractAction {

    private final String className;

    public SetLookAndFeelAction(final String name, final String className) {
        super(name);
        this.className = className;
        
        this.putValue(SHORT_DESCRIPTION, "Set Look and Feel to " + name);
        this.putValue(LONG_DESCRIPTION, "Set the current Look and Feel to " + name + ".");
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        assert EventQueue.isDispatchThread();
        
        try {
            UIManager.setLookAndFeel(className);
            
            for (final Frame frame : Frame.getFrames()) {
                SwingUtilities.updateComponentTreeUI(frame);
            }
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}
