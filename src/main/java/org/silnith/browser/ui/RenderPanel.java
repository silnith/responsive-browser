package org.silnith.browser.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;


/**
 * The panel to display the rendered content and controls associated with the
 * rendering task.  This is a generic container that can hold any generic rendered
 * content produced by an external renderer.  This panel provides controls that
 * allow a standardized interface for interacting with the render task.
 */
public class RenderPanel extends JPanel {
    
    private final JPanel progressPanel;
    
    private final JProgressBar progressBar;
    
    private final DocumentPanel documentPanel;
//    private final JPanel documentPanel;
    
    public RenderPanel() {
        super(new BorderLayout());
        this.progressPanel = new JPanel(new BorderLayout());
        this.progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        this.documentPanel = new DocumentPanel();
//        this.documentPanel = new JPanel(new BorderLayout());
    }
    
    /**
     * Initializes the render panel.  Must be called from the event dispatch thread.
     */
    public void initialize() {
        assert EventQueue.isDispatchThread();
        
        documentPanel.setPreferredSize(new Dimension(800, 600));
        
        final FontMetrics fontMetrics = documentPanel.getFontMetrics(documentPanel.getFont().deriveFont(14.0f));
        
        final JScrollPane scrollPane = new JScrollPane(documentPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(
                fontMetrics.getAscent() + fontMetrics.getDescent() + fontMetrics.getLeading());
                
        progressPanel.add(progressBar, BorderLayout.CENTER);
        
        this.add(progressPanel, BorderLayout.PAGE_START);
        this.add(scrollPane, BorderLayout.CENTER);
        
        final SwingWorker<String, String> loadTask = new SwingWorker<String, String>() {
            
            @Override
            protected String doInBackground() throws Exception {
                assert !EventQueue.isDispatchThread();
                
                final URL url = new URL("file:///C:/Users/kent/Downloads/HTML5.html");
//                final URL url = new URL("file:///C:/Users/kent/Downloads/HTML5.txt");
//                final URL url = new URL("http://www.i18nguy.com/unicode-example.html");
//                final URL url = new URL("file:///C:/Users/kent/Downloads/unicode-example-utf8.txt");
                
                final URLConnection connection = url.openConnection();
                
                try (final StringWriter writer = new StringWriter()) {
                    try (final Reader reader = new InputStreamReader(connection.getInputStream(), "UTF-8")) {
                        final char[] buffer = new char[8192];
                        int charsRead = reader.read(buffer);
                        while (charsRead != -1) {
                            writer.write(buffer, 0, charsRead);
                            
                            charsRead = reader.read(buffer);
                        }
                    }
                    return writer.toString();
                }
            }
            
            @Override
            protected void done() {
                try {
                    setText(get());
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                } catch (final ExecutionException e) {
                    e.printStackTrace();
                }
            }
            
        };
        
//        loadTask.execute();
    }
    
    public void setText(final String text) {
        assert EventQueue.isDispatchThread();
        
        final RenderTask renderTask = new RenderTask(800.0f, text);
        renderTask.addPropertyChangeListener(new WorkerListener());
        
        progressPanel.add(new JButton(renderTask.getCancelAction()), BorderLayout.LINE_END);
        renderTask.getCancelAction();
        
        renderTask.execute();
    }
    
    private class WorkerListener implements PropertyChangeListener {
        
        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            final Object oldValue = evt.getOldValue();
            final Object newValue = evt.getNewValue();
            
            switch (evt.getPropertyName()) {
            case "state": {
                final SwingWorker.StateValue oldState = (SwingWorker.StateValue) oldValue;
                final SwingWorker.StateValue newState = (SwingWorker.StateValue) newValue;
                
                switch (newState) {
                case PENDING: {
                }
                    break;
                case STARTED: {
                    progressBar.setStringPainted(true);
                }
                    break;
                case DONE: {
                }
                    break;
                default: {
                }
                    break;
                }
            }
                break;
            case "progress": {
                final int oldProgress = (int) oldValue;
                final int newProgress = (int) newValue;
                
                RenderPanel.this.progressBar.setValue(newProgress);
            }
                break;
            default: {
            }
                break;
            }
        }
        
    }
    
    private class RenderTask extends SwingWorker<List<TextLayout>, TextLayout> {
        
        private class CancelAction extends AbstractAction {
            
            public CancelAction() {
                super("Cancel Render");
                
                this.putValue(SHORT_DESCRIPTION, "Cancel Render");
                this.putValue(LONG_DESCRIPTION, "Cancel the current render task.");
            }
            
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                assert EventQueue.isDispatchThread();
                
                RenderTask.this.cancel(true);
            }
            
        }
        
        private final float desiredWidth;
        
        private final String text;
        
        public RenderTask(final float desiredWidth, final String text) {
            super();
            this.desiredWidth = desiredWidth;
            this.text = text;
        }
        
        public Action getCancelAction() {
            return new CancelAction();
        }
        
        @Override
        protected List<TextLayout> doInBackground() throws Exception {
            assert !EventQueue.isDispatchThread();
            
            final String[] lines = text.split("\r\n|\r|\n");
            int tempLength = 0;
            for (final String line : lines) {
                tempLength += line.length();
            }
            
            final int documentLength = tempLength;
            
            final boolean useIntegerMath = (Integer.MAX_VALUE / 100 >= documentLength);
            
            final List<TextLayout> layouts = new LinkedList<TextLayout>();
            
            int lineOffset = 0;
            for (final String line : lines) {
                final AttributedCharacterIterator characterIterator;
                if (line.isEmpty()) {
                    characterIterator = new AttributedString(" ").getIterator();
                } else {
                    characterIterator = new AttributedString(line).getIterator();
                }
                
                final BreakIterator breakIter = BreakIterator.getLineInstance();
                final FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_ON,
                        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                final LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(characterIterator, breakIter, frc);
                
                final int beginIndex = characterIterator.getBeginIndex();
                final int endIndex = characterIterator.getEndIndex();
                
                final int length = endIndex - beginIndex;
                
                TextLayout nextLayout = lineBreakMeasurer.nextLayout(desiredWidth);
                while (nextLayout != null) {
                    layouts.add(nextLayout);
                    final int index = lineBreakMeasurer.getPosition();
                    final int offset = lineOffset + index - beginIndex;
                    
                    if (useIntegerMath) {
                        final int percent = (offset * 100) / documentLength;
                        this.setProgress(percent);
                    } else {
                        final float floatOffset = offset;
                        final float percent = (floatOffset * 100f) / documentLength;
                        this.setProgress((int) percent);
                    }
                    
                    this.publish(nextLayout);
                    
                    if (Thread.interrupted()) {
                        System.out.println("Cancelled!");
                        return layouts;
                    }
                    
                    nextLayout = lineBreakMeasurer.nextLayout(desiredWidth);
                }
                
                lineOffset += length;
            }
            
            return layouts;
        }
        
        @Override
        protected void process(final List<TextLayout> chunks) {
            assert EventQueue.isDispatchThread();
            
            for (final TextLayout nextLayout : chunks) {
                documentPanel.addTextLayout(nextLayout);
            }
        }
        
        @Override
        protected void done() {
            assert EventQueue.isDispatchThread();
            
            progressPanel.remove(1);
            
            RenderPanel.this.revalidate();
            RenderPanel.this.repaint();
        }
        
    }
    
}
