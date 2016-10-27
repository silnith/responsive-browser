package org.silnith.browser.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


/**
 * A panel that displays a plain-text document as a vertical sequence of
 * {@link TextLayout} objects.
 */
public class DocumentPanel extends JPanel {
    
    private final List<TextLayout> textLayouts;
    
    private final List<Point2D.Float> textPositions;
    
    private float totalWidth;
    
    private float totalHeight;
    
    public DocumentPanel() {
        super();
        this.textLayouts = new ArrayList<TextLayout>();
        this.textPositions = new ArrayList<Point2D.Float>();
        this.totalWidth = 0f;
        this.totalHeight = 0f;
        
        this.setOpaque(false);
    }
    
    /**
     * Adds a new line of text to this panel's displayed content.  Must be called
     * from the event dispatch thread.
     * 
     * @param textLayout the new line of text
     */
    public void addTextLayout(final TextLayout textLayout) {
        assert EventQueue.isDispatchThread();
        final float ascent = textLayout.getAscent();
        final float descent = textLayout.getDescent();
        final float leading = textLayout.getLeading();
        final float visibleAdvance = textLayout.getVisibleAdvance();
        
        textLayouts.add(textLayout);
        final Point2D.Float position = new Point2D.Float(0, totalHeight + ascent + leading * 0.5f);
        textPositions.add(position);
        
        final float layoutWidth = visibleAdvance;
        final float layoutHeight = ascent + descent + leading;
        final Dimension dimension = new Dimension((int) Math.ceil(layoutWidth), (int) Math.ceil(layoutHeight));
        
        totalWidth = Math.max(totalWidth, layoutWidth);
        totalHeight += layoutHeight;
        
        final Dimension preferredSize = new Dimension((int) Math.ceil(totalWidth), (int) Math.ceil(totalHeight));
        this.setPreferredSize(preferredSize);
        
        final Rectangle dirtyRegion = new Rectangle((int) Math.floor(position.getX()), (int) Math.floor(position.getY()), dimension.width, dimension.height);
        
        this.revalidate();
        this.repaint(dirtyRegion);
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        
        final Graphics2D g2 = (Graphics2D) g;
        
        final Rectangle visibleRect = getVisibleRect();
        
        assert textLayouts.size() == textPositions.size();
        
        for (int i = 0; i < textLayouts.size(); i++ ) {
            final TextLayout textLayout = textLayouts.get(i);
            final Float position = textPositions.get(i);
            
            final Rectangle pixelBounds = textLayout.getPixelBounds(null, position.x, position.y);
            
            if (visibleRect.intersects(pixelBounds)) {
                textLayout.draw(g2, position.x, position.y);
            }
        }
    }
    
//    @Override
//    public Dimension getPreferredScrollableViewportSize() {
//        // TODO Auto-generated method stub
//        return null;
//    }

//    @Override
//    public int getScrollableBlockIncrement(final Rectangle visibleRect,
//            final int orientation, final int direction) {
//        // TODO Auto-generated method stub
//        return 0;
//    }

//    @Override
//    public boolean getScrollableTracksViewportHeight() {
//        return false;
//    }

//    @Override
//    public boolean getScrollableTracksViewportWidth() {
//        // TODO Auto-generated method stub
//        return false;
//    }

//    @Override
//    public int getScrollableUnitIncrement(final Rectangle visibleRect,
//            final int orientation, final int direction) {
//        // TODO Auto-generated method stub
//        return 0;
//    }

}
