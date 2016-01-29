package org.silnith.browser.model;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import org.silnith.browser.ui.NavigationPanel;


public class NavigationListener implements ActionListener {

    private final BrowsingContext browsingContext;

    private final NavigationPanel navigationPanel;

    public NavigationListener(final BrowsingContext browsingContext, final NavigationPanel navigationPanel) {
        super();
        this.browsingContext = browsingContext;
        this.navigationPanel = navigationPanel;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        assert EventQueue.isDispatchThread();
        
        navigationPanel.clearErrors();
        
        final String location = event.getActionCommand();
        
        try {
            final URL url = new URL(location);
            
            final NavigationRequest navigationRequest = new NavigationRequest(url);
            
            browsingContext.startNewNavigation(navigationRequest);
        } catch (final MalformedURLException e) {
            navigationPanel.showError(e);
        }
    }

}
