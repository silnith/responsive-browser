package org.silnith.browser.network;

import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.silnith.browser.model.DownloadRequest;


public class FileCacheDownloadManager implements DownloadManager {
    
    private final ExecutorService executorService;
    
    public FileCacheDownloadManager() {
        super();
        this.executorService = Executors.newCachedThreadPool();
    }
    
    @PostConstruct
    public void initialize() {
    }
    
    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
    
//    @Override
    public Future<Reader> download(final URL url) {
        return executorService.submit(new DownloadTask(url, 8192));
    }
    
    @Override
    public Download download(final DownloadRequest downloadRequest) {
        return new DownloadStatus();
    }
    
    private static class DownloadStatus implements Download {
        
        private final Date startTime;
        
        public DownloadStatus() {
            super();
            this.startTime = new Date();
        }
        
        @Override
        public Date getTimestamp() {
            return (Date) startTime.clone();
        }
        
        @Override
        public long getDuration() {
            return 0;
        }
        
        @Override
        public InputStream getInputStream() {
            return null;
        }
        
        @Override
        public void addDownloadListener(final Object listener) {
            ;
        }
        
    }
    
}
