package org.silnith.browser.model;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.SwingWorker;

import org.silnith.browser.network.CacheEntry;
import org.silnith.browser.network.CacheManager;
import org.silnith.browser.network.Download;

public class BrowsingContext implements PropertyChangeListener {

    private final CacheManager cacheManager;

    private final List<NavigationResult> history;

    private final AtomicReference<NavigateTask> navigationTask;

    private final ExecutorService executorService;

    public BrowsingContext(final CacheManager cacheManager) {
        super();
        this.cacheManager = cacheManager;
        this.history = new ArrayList<NavigationResult>();
        this.navigationTask = new AtomicReference<NavigateTask>();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public NavigationResult getCurrentNavigationResult() {
        if (history.isEmpty()) {
            return null;
        } else {
//            return history.listIterator(history.size()).previous();
            return history.get(history.size() - 1);
        }
    }

    public void startNewNavigation(final NavigationRequest navigationRequest) {
        assert EventQueue.isDispatchThread();
        
        final NavigationResult navigationResult = new NavigationResult(navigationRequest);
        
        history.add(navigationResult);
        
        final NavigateTask navigateTask = new NavigateTask(navigationResult);
        
        final NavigateTask previousNavigationTask = navigationTask.getAndSet(navigateTask);
        if (previousNavigationTask != null) {
            previousNavigationTask.cancel(true);
            previousNavigationTask.removePropertyChangeListener(this);
        }
        
        navigateTask.addPropertyChangeListener(this);
        
//        navigateTask.execute();
//        final Callable<?> callable = navigateTask;
//        final Future<String> future = executorService.submit((Callable<String>) navigateTask);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        assert EventQueue.isDispatchThread();
        
        final String propertyName = evt.getPropertyName();
        final Object oldValue = evt.getOldValue();
        final Object newValue = evt.getNewValue();
        final Object source = evt.getSource();
        
        System.out.print(propertyName);
        System.out.print(": ");
        System.out.print(oldValue);
        System.out.print(" -> ");
        System.out.println(newValue);
        
        switch (propertyName) {
        case "state": {
            handleStateChange((NavigateTask) source, (SwingWorker.StateValue) oldValue, (SwingWorker.StateValue) newValue);
        } break;
        case "progress": {
            handleProgress((NavigateTask) source, (int) oldValue, (int) newValue);
        } break;
        default: {} break;
        }
    }

    private void handleStateChange(final NavigateTask source, final SwingWorker.StateValue oldState, final SwingWorker.StateValue newState) {
        assert EventQueue.isDispatchThread();
        
        switch (newState) {
        case PENDING: {} break;
        case STARTED: {} break;
        case DONE: {
            source.removePropertyChangeListener(this);
        } break;
        default: {} break;
        }
    }

    private void handleProgress(final NavigateTask source, final int oldValue, final int newValue) {
        assert EventQueue.isDispatchThread();
        
        System.out.print("Worker task progress: ");
        System.out.println(newValue);
    }

    private class NavigateTask extends SwingWorker<String, Integer> {

        private final NavigationResult navigationResult;

        public NavigateTask(final NavigationResult navigationResult) {
            super();
            this.navigationResult = navigationResult;
        }

        @Override
        protected String doInBackground() throws Exception {
            assert !EventQueue.isDispatchThread();
            
            final NavigationRequest navigationRequest = navigationResult.getNavigationRequest();
            
            /*
             * First, ask the cache manager if the URL is already cached.
             */
            
            final CacheEntry cacheEntry = cacheManager.getCacheEntry(navigationRequest.getURL());
            
            /*
             * If the cache entry does not have the content yet, ask the download
             * manager to download the file and populate the cache entry.
             */
            
            final Download download = cacheEntry.offerDownload(null, null).get();
            
            download.addDownloadListener(null);
            
            /*
             * Ask the parser factory for a parser for the content type.
             */
            
            /*
             * Parse the content in a SwingWorker.
             */
            
            /*
             * Ask the renderer factory for a renderer for the content type.
             */
            
            /*
             * Pass the renderer's output panel to the render listeners.
             */
            
            /*
             * Start the renderer in a SwingWorker.
             * 
             * The SwingWorker will fire update events to the render listeners.
             */
            
            // update listeners to re-render
            
            final URL url = new URL("file:///C:/Users/kent/Downloads/HTML5.html");
            
            final URLConnection connection = url.openConnection();
            
            System.out.println(connection.getContentLengthLong());
            
            try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(connection.getContentLength())) {
                try (final InputStream inputStream = connection.getInputStream()) {
                    final byte[] buffer = new byte[8192];
                    int bytesRead = inputStream.read(buffer);
                    while (bytesRead != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        
                        this.publish(outputStream.size());
                        
                        bytesRead = inputStream.read(buffer);
                    }
                }
                outputStream.toByteArray();
            }
            
            return "done";
        }

        @Override
        protected void process(final List<Integer> chunks) {
            assert EventQueue.isDispatchThread();
            
            System.out.print("Processing: ");
            System.out.println(chunks);
        }

        @Override
        protected void done() {
            assert EventQueue.isDispatchThread();
            
            if (this.isCancelled()) {
                System.out.println("Download Cancelled");
            } else if (this.isDone()) {
                System.out.println("Download Completed");
            } else {
                System.out.println("What happened?");
            }
        }

    }

}
