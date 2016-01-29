package org.silnith.browser;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.silnith.browser.network.CacheManager;
import org.silnith.browser.network.DownloadManager;
import org.silnith.browser.network.FileCacheDownloadManager;
import org.silnith.browser.network.InMemoryCacheManager;
import org.silnith.browser.ui.BrowserWindow;
import org.silnith.browser.ui.action.ExitBrowserAction;
import org.silnith.browser.ui.action.NewWindowAction;

public class ResponsiveBrowser {

    private final CacheManager cacheManager;

    private final DownloadManager downloadManager;

    private final NewWindowAction newWindowAction;

    private final ExitBrowserAction exitBrowserAction;

    public ResponsiveBrowser(final CacheManager cacheManager, final DownloadManager downloadManager) {
        super();
        this.cacheManager = cacheManager;
        this.downloadManager = downloadManager;
        this.newWindowAction = new NewWindowAction(this);
        this.exitBrowserAction = new ExitBrowserAction(this);
    }

    public void createNewWindow() {
        assert EventQueue.isDispatchThread();
        
        final BrowserWindow browserWindow = new BrowserWindow(cacheManager, newWindowAction, exitBrowserAction);
        browserWindow.initialize();
        
        browserWindow.pack();
        browserWindow.setVisible(true);
    }

    public static void main(final String[] args) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException, InvocationTargetException,
            InterruptedException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
        final ResponsiveBrowser responsiveBrowser = new ResponsiveBrowser(new InMemoryCacheManager(), new FileCacheDownloadManager());
        SwingUtilities.invokeAndWait(new Runnable() {
            
            @Override
            public void run() {
                assert EventQueue.isDispatchThread();
                
                responsiveBrowser.createNewWindow();
            }
            
        });
    }

}
