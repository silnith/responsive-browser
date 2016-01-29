package org.silnith.browser.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.silnith.browser.model.BrowsingContext;
import org.silnith.browser.model.NavigationListener;

public class NavigationPanel extends JPanel {

    private final JTextField urlBar;

    private JLabel errorPanel;

    public NavigationPanel() {
        super(new BorderLayout());
        this.urlBar = new JTextField("http://www.w3.org/TR/html4/charset.html");
        this.errorPanel = null;
    }

    public void initialize(final BrowsingContext browsingContext) {
        assert EventQueue.isDispatchThread();
        
        urlBar.addActionListener(new NavigationListener(browsingContext, this));
        
        final JLabel urlLabel = new JLabel("URL");
        urlLabel.setLabelFor(urlBar);
        urlLabel.setDisplayedMnemonic(KeyEvent.VK_L);
        
        final JPanel urlPanel = new JPanel(new BorderLayout());
        urlPanel.add(urlLabel, BorderLayout.LINE_START);
        urlPanel.add(urlBar, BorderLayout.CENTER);
        
        this.add(urlPanel, BorderLayout.PAGE_START);
    }

    public void clearErrors() {
        assert EventQueue.isDispatchThread();
        
        if (errorPanel != null) {
            this.remove(errorPanel);
            
            this.revalidate();
        }
        errorPanel = null;
    }

    public void showError(final MalformedURLException exception) {
        assert EventQueue.isDispatchThread();
        
        if (errorPanel != null) {
            System.err.println("Duplicate error panel found.");
            this.remove(errorPanel);
        }
        
        errorPanel = new JLabel(exception.getLocalizedMessage());
        
        this.add(errorPanel, BorderLayout.CENTER);
        
        this.revalidate();
    }

}
