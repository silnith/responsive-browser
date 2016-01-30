package org.silnith.browser.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class ImageRenderPanel extends JPanel {
    
    private BufferedImage image;
    
    public ImageRenderPanel() {
        super(new BorderLayout());
    }
    
    public void initialize() {
        assert EventQueue.isDispatchThread();
        
//        final BufferedImage image;
        try {
            image = ImageIO.read(new URL("file:///C:/Users/kent/Downloads/18bskg.jpg"));
        } catch (final IOException e) {
            System.exit( -1);
            return;
        }
        
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        
        final Graphics2D g2 = (Graphics2D) g;
        
        g.drawImage(image, 0, 0, null);
    }
    
    public static void main(final String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            
            @Override
            public void run() {
                final ImageRenderPanel imageRenderPanel = new ImageRenderPanel();
                imageRenderPanel.initialize();
                
                final JFrame frame = new JFrame("test");
                frame.setContentPane(imageRenderPanel);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                
                frame.pack();
                frame.setVisible(true);
            }
            
        });
    }
    
}
