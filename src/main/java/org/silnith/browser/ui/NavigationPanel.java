package org.silnith.browser.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.silnith.browser.model.BrowsingContext;
import org.silnith.browser.model.NavigationListener;


/**
 * A panel that displays the URL and associated information needed to start a
 * download.
 */
public class NavigationPanel extends JPanel {
    
    private final JTextField urlBar;
    
    private JLabel errorPanel;
    
    public NavigationPanel() {
        super(new BorderLayout());
        this.urlBar = new JTextField("http://www.w3.org/TR/html4/charset.html");
        this.errorPanel = null;
    }
    
    /**
     * Initializes this navigation panel.  Must be called from the event dispatch thread.
     * 
     * @param browsingContext the browsing context to which this navigation panel
     *         will end events
     */
    public void initialize(final BrowsingContext browsingContext) {
        assert EventQueue.isDispatchThread();
        
        urlBar.addActionListener(new NavigationListener(browsingContext, this));
        
        final JPanel urlPanel = createURLPanel();
        
        final JPanel breakdownPanel = createBreakdownPanel();
        
        this.add(urlPanel, BorderLayout.PAGE_START);
        this.add(breakdownPanel, BorderLayout.PAGE_END);
    }

    private JPanel createBreakdownPanel() {
        final JComboBox<String> protocolBox = new JComboBox<>();
        protocolBox.setEditable(true);
        protocolBox.addItem("http");
        protocolBox.addItem("https");
        protocolBox.addItem("file");
        
        final JLabel protocolLabel = new JLabel("Protocol");
        protocolLabel.setLabelFor(protocolBox);
        protocolLabel.setDisplayedMnemonic(KeyEvent.VK_P);
        
        final JTextField hostField = new JTextField();
        hostField.setText("www.w3.org");
        
        final JLabel hostLabel = new JLabel("Host");
        hostLabel.setLabelFor(hostField);
        hostLabel.setDisplayedMnemonic(KeyEvent.VK_H);
        
        final JTextField portField = new JTextField(5);
        portField.setText("80");
        
        final JLabel portLabel = new JLabel("Port");
        portLabel.setLabelFor(portField);
        
        final JLabel pathLabel = new JLabel("Path");
//        pathLabel.setLabelFor(pathTable);
        
        final JPanel breakdownPanel = new JPanel();
        breakdownPanel.add(protocolLabel);
        breakdownPanel.add(protocolBox);
        breakdownPanel.add(hostLabel);
        breakdownPanel.add(hostField);
        breakdownPanel.add(portLabel);
        breakdownPanel.add(portField);
        breakdownPanel.add(pathLabel);
//        breakdownPanel.add(pathTable);
        return breakdownPanel;
    }

    private JPanel createURLPanel() {
        final JLabel urlLabel = new JLabel("URL");
        urlLabel.setLabelFor(urlBar);
        urlLabel.setDisplayedMnemonic(KeyEvent.VK_L);
        
        final JPanel urlPanel = new JPanel(new BorderLayout());
        urlPanel.add(urlLabel, BorderLayout.LINE_START);
        urlPanel.add(urlBar, BorderLayout.CENTER);
        return urlPanel;
    }
    
    /**
     * Clears any currently displayed errors.  Must be called from the event dispatch thread.
     */
    public void clearErrors() {
        assert EventQueue.isDispatchThread();
        
        if (errorPanel != null) {
            this.remove(errorPanel);
            
            this.revalidate();
        }
        errorPanel = null;
    }
    
    /**
     * Shows an error on the navigation panel.  Must be called from the event
     * dispatch thread.
     * 
     * @param exception the error to display
     */
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
    
    public static void main(final String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            
            @Override
            public void run() {
                final JFrame jFrame = new JFrame("Navigation Panel demo");
                jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                
                final NavigationPanel navigationPanel = new NavigationPanel();
                navigationPanel.initialize(null);
                jFrame.getContentPane().add(navigationPanel);
                
                ;
                
                jFrame.pack();
                jFrame.setVisible(true);
            }
            
        });
    }
    
}
