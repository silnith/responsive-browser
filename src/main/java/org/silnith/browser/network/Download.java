package org.silnith.browser.network;

import java.io.InputStream;
import java.util.Date;

public interface Download {

    /**
     * Returns the date at which the download was initiated.
     * 
     * @return the date that the download started
     */
    Date getTimestamp();

    /**
     * Returns the number of milliseconds that elapsed during the download.
     * 
     * @return the number of milliseconds that the download took
     */
    long getDuration();

    /**
     * Returns an input stream that can be used to read the contents of the
     * download.
     * 
     * @return the downloaded content as a readable input stream
     */
    InputStream getInputStream();

    void addDownloadListener(Object listener);

}
