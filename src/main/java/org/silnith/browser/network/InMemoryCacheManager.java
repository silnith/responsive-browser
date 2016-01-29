package org.silnith.browser.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

public class InMemoryCacheManager implements CacheManager {

    private final ConcurrentMap<String, CacheEntry> cache;

    public InMemoryCacheManager() {
        super();
        this.cache = new ConcurrentHashMap<String, CacheEntry>();
    }

    @Override
    public CacheEntry getCacheEntry(final URL url) {
        final MemoryCacheEntry memoryCacheEntry = new MemoryCacheEntry(url);
        
        final CacheEntry previousCacheEntry = cache.putIfAbsent(url.toString(), memoryCacheEntry);
        
        if (previousCacheEntry == null) {
            return memoryCacheEntry;
        } else {
            return previousCacheEntry;
        }
    }

    private static class MemoryCacheEntry implements CacheEntry {

        private final DownloadManager downloadManager;

        private final URL url;

        private final List<Download> downloads;

        public MemoryCacheEntry(final URL url) {
            super();
            this.downloadManager = null;
            this.url = url;
            this.downloads = new ArrayList<Download>();
        }

        @Override
        public URL getURL() {
            return url;
        }

        @Override
        public List<Download> getAllDownloads() {
            return Collections.unmodifiableList(downloads);
        }

        @Override
        public synchronized Download getLatestDownload() {
            return downloads.get(downloads.size() - 1);
        }

        @Override
        public synchronized Future<Download> offerDownload(final DownloadManager downloadManager, final Callable<InputStream> downloadTask) {
            if (downloads.isEmpty()) {
                try {
                final InputStream result = downloadTask.call();
                final CopyContent copyTask = new CopyContent(result);
                final Download download = copyTask.call();
                downloads.add(download);
//                return download;
                return null;
                } catch (final Exception e) {
                    return null;
                }
            } else {
                return new FutureTask<Download>(new Runnable() {
                    @Override
                    public void run() {}
                }, getLatestDownload());
            }
        }

        @Override
        public synchronized Future<Download> addDownload(final InputStream content) {
            return new FutureTask<Download>(new CopyContent(content));
        }

        private static class MemoryDownload implements Download {

            @Override
            public Date getTimestamp() {
                return null;
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
            public void addDownloadListener(Object listener) {
                ;
            }

        }

        private static class CopyContent implements Callable<Download> {

            private final InputStream inputStream;

            public CopyContent(final InputStream inputStream) {
                super();
                this.inputStream = inputStream;
            }

            @Override
            public Download call() throws Exception {
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                int ch = inputStream.read();
                while (ch != -1) {
                    out.write(ch);
                    ch = inputStream.read();
                }
                return new ByteArrayDownload(out.toByteArray());
            }

        }

        private static class ByteArrayDownload implements Download {

            private final Date started;

            private final byte[] content;

            public ByteArrayDownload(final byte[] content) {
                super();
                this.started = new Date();
                this.content = content;
            }

            @Override
            public Date getTimestamp() {
                return new Date(started.getTime());
            }

            @Override
            public long getDuration() {
                return 0;
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream(content);
            }

            @Override
            public void addDownloadListener(Object listener) {
                ;
            }

        }

        private static class ProgressDownload extends SwingWorker<Download, Integer> {

            @Override
            protected Download doInBackground() throws Exception {
                return null;
            }

        }

    }

}
