package org.silnith.browser.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.silnith.browser.model.BrowsingContext;
import org.silnith.browser.network.CacheManager;


/**
 * A panel to hold and display a single browsing context.  This includes:
 * <ul>
 *   <li>the navigation pane that controls where this browsing context navigates
 *     to and displays what content is currently being displayed
 *   <li>the render pane that displays the downloaded and rendered content
 *   <li>a configuration panel that allows configuring how this browsing context
 *     will download and render content (overriding default application-wide configuration)
 *   <li>a download tracking panel that displays downloads that are currently
 *     associated with this browsing context
 * </ul>
 */
public class BrowsingPanel extends JPanel {
    
    private final NavigationPanel navigationPanel;
    
    private final RenderPanel renderPanel;
    
    private final JComponent configurationPanel;
    
    private final JComponent downloadsPanel;
    
    private final BrowsingContext browsingContext;
    
    public BrowsingPanel(final CacheManager cacheManager) {
        super(new BorderLayout());
        this.navigationPanel = new NavigationPanel();
        this.renderPanel = new RenderPanel();
        this.configurationPanel = new JPanel();
        this.downloadsPanel = new JPanel();
        this.browsingContext = new BrowsingContext(cacheManager);
    }
    
    /**
     * Initializes this browsing panel.  Must be called from the event dispatch thread.
     */
    public void initialize() {
        assert EventQueue.isDispatchThread();
        
        final JTabbedPane informationPanel = new JTabbedPane();
        
        downloadsPanel.setPreferredSize(new Dimension(300, 200));
        
        configurationPanel.setPreferredSize(new Dimension(300, 200));
        
        informationPanel.addTab("Downloads", downloadsPanel);
        informationPanel.addTab("Configuration", configurationPanel);
        
        renderPanel.initialize();
        
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, renderPanel, informationPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(1.0);
        
        navigationPanel.initialize(browsingContext);
        
        this.add(navigationPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
    }
    
}
