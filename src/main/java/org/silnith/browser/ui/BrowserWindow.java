package org.silnith.browser.ui;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.silnith.browser.network.CacheManager;
import org.silnith.browser.ui.action.CloseTabAction;
import org.silnith.browser.ui.action.CloseWindowAction;
import org.silnith.browser.ui.action.ExitBrowserAction;
import org.silnith.browser.ui.action.NewTabAction;
import org.silnith.browser.ui.action.NewWindowAction;
import org.silnith.browser.ui.action.SetLookAndFeelAction;
import org.silnith.browser.ui.action.ShrinkMemoryAction;
import org.silnith.browser.ui.action.tab.TabBottomAction;
import org.silnith.browser.ui.action.tab.TabLeftAction;
import org.silnith.browser.ui.action.tab.TabRightAction;
import org.silnith.browser.ui.action.tab.TabScrollAction;
import org.silnith.browser.ui.action.tab.TabTopAction;
import org.silnith.browser.ui.action.tab.TabWrapAction;


/**
 * A single browser window in the responsive browser.  This establishes a root
 * for window-based actions, but shares a download manager and cache manager
 * with all other windows.
 */
public class BrowserWindow extends JFrame {
    
    private final CacheManager cacheManager;
    
    private final NewWindowAction newWindowAction;
    
    private final ExitBrowserAction exitBrowserAction;
    
    private final JTabbedPane tabbedPane;
    
    public BrowserWindow(final CacheManager cacheManager, final NewWindowAction newWindowAction,
            final ExitBrowserAction exitBrowserAction) {
        super("Browser Window");
        this.cacheManager = cacheManager;
        this.newWindowAction = newWindowAction;
        this.exitBrowserAction = exitBrowserAction;
        this.tabbedPane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationByPlatform(true);
    }
    
    /**
     * Initializes the browser window.  Must be called in the event dispatch thread.
     */
    public void initialize() {
        assert EventQueue.isDispatchThread();
        
        this.setJMenuBar(createMenuBar());
        
        this.setContentPane(tabbedPane);
        createNewTab();
    }
    
    /**
     * Creates a menubar for the window.
     * 
     * @return the menubar
     */
    private JMenuBar createMenuBar() {
        final JMenu browserMenu = new JMenu("Browser");
        browserMenu.setMnemonic(KeyEvent.VK_B);
        browserMenu.add(newWindowAction);
        browserMenu.add(new ShrinkMemoryAction());
        browserMenu.addSeparator();
        browserMenu.add(exitBrowserAction);
        
        final JMenu windowMenu = new JMenu("Window");
        windowMenu.setMnemonic(KeyEvent.VK_W);
        windowMenu.add(new NewTabAction(this));
        windowMenu.add(createTabPlacementMenu());
        windowMenu.add(createTabLayoutPolicyMenu());
        windowMenu.addSeparator();
        windowMenu.add(new CloseWindowAction(this));
        
        final JMenu tabMenu = new JMenu("Tab");
        tabMenu.setMnemonic(KeyEvent.VK_T);
        tabMenu.add(new CloseTabAction(this));
        
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(browserMenu);
        menuBar.add(windowMenu);
        menuBar.add(tabMenu);
        menuBar.add(createLookAndFeelMenu());
        return menuBar;
    }
    
    /**
     * Creates the menu that allows specifying where the tab placement will be
     * for the tabbed pane for this window.
     * 
     * @return the tab placement menu
     */
    private JMenu createTabPlacementMenu() {
        final JRadioButtonMenuItem topMenuItem = new JRadioButtonMenuItem(new TabTopAction(tabbedPane));
        final JRadioButtonMenuItem leftMenuItem = new JRadioButtonMenuItem(new TabLeftAction(tabbedPane));
        final JRadioButtonMenuItem rightMenuItem = new JRadioButtonMenuItem(new TabRightAction(tabbedPane));
        final JRadioButtonMenuItem bottomMenuItem = new JRadioButtonMenuItem(new TabBottomAction(tabbedPane));
        
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(topMenuItem);
        buttonGroup.add(leftMenuItem);
        buttonGroup.add(rightMenuItem);
        buttonGroup.add(bottomMenuItem);
        
        final JMenu menu = new JMenu("Tab Placement");
        menu.setMnemonic(KeyEvent.VK_P);
        
        menu.add(topMenuItem);
        menu.add(leftMenuItem);
        menu.add(rightMenuItem);
        menu.add(bottomMenuItem);
        
        return menu;
    }
    
    /**
     * Creates the menu that allows specifying the tab layout policy for the
     * tabbed pane for this window.
     * 
     * @return the tab policy menu
     */
    private JMenu createTabLayoutPolicyMenu() {
        final JRadioButtonMenuItem wrapMenuItem = new JRadioButtonMenuItem(new TabWrapAction(tabbedPane));
        final JRadioButtonMenuItem scrollMenuItem = new JRadioButtonMenuItem(new TabScrollAction(tabbedPane));
        
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(wrapMenuItem);
        buttonGroup.add(scrollMenuItem);
        
        final JMenu menu = new JMenu("Tab Overflow Layout");
        menu.setMnemonic(KeyEvent.VK_O);
        
        menu.add(wrapMenuItem);
        menu.add(scrollMenuItem);
        
        return menu;
    }
    
    /**
     * Creates the menu that allows setting the overall Look &amp; Feel for the
     * application.
     * 
     * @return the look and feel menu
     */
    private JMenu createLookAndFeelMenu() {
        final JMenu menu = new JMenu("Look And Feel");
        menu.setMnemonic(KeyEvent.VK_L);
        for (final LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            final SetLookAndFeelAction setLookAndFeelAction =
                    new SetLookAndFeelAction(info.getName(), info.getClassName());
            menu.add(setLookAndFeelAction);
        }
        return menu;
    }
    
    /**
     * Creates a new tab in this window.  Must be called in the event dispatch
     * thread.
     */
    public void createNewTab() {
        assert EventQueue.isDispatchThread();
        
        final BrowsingPanel browsingPanel = new BrowsingPanel(cacheManager);
        browsingPanel.initialize();
        
        tabbedPane.addTab("New Tab", browsingPanel);
    }
    
    public int getNumberOfTabs() {
        return tabbedPane.getTabCount();
    }
    
    /**
     * Closes and disposes of the currently active tab in this window.  Must be
     * called in the event dispatch thread.
     */
    public void closeCurrentTab() {
        assert EventQueue.isDispatchThread();
        
        tabbedPane.remove(tabbedPane.getSelectedComponent());
        
        if (tabbedPane.getTabCount() == 0) {
            closeWindow();
        }
    }
    
    /**
     * Closes and disposes of this window.  Must be called in the event dispatch
     * thread.
     */
    public void closeWindow() {
        assert EventQueue.isDispatchThread();
        
        this.dispose();
    }
    
}
