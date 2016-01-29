package org.silnith.browser.network;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface CacheEntry {

    URL getURL();

    List<Download> getAllDownloads();

    Download getLatestDownload();

    Future<Download> offerDownload(DownloadManager downloadManager, Callable<InputStream> downloadTask);

    Future<Download> addDownload(InputStream content);

}
