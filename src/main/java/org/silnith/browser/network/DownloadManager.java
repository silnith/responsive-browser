package org.silnith.browser.network;

import org.silnith.browser.model.DownloadRequest;


public interface DownloadManager {

    Download download(DownloadRequest downloadRequest);

}
