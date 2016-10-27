package org.silnith.browser.network;

import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.SwingWorker;


/**
 * A SwingWorker that transfers bytes from an InputStream to an OutputStream
 * while reporting on its progress.
 */
public class TransferWorker extends SwingWorker<Object, Long> {
    
    private final InputStream inputStream;
    private final OutputStream outputStream;
    
    public TransferWorker(final InputStream inputStream, final OutputStream outputStream) {
        super();
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    protected Object doInBackground() throws Exception {
        return null;
    }

}
