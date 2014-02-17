package au.net.genesis.mds.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PreviewPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
			
	/**
	 * Creates a JPanel that can be used to display an image
	 * 
	 * @param image the initial image to display
	 */
	public PreviewPanel() {
		this.setPreferredSize(new Dimension(256,256));
	}
	
	
	/**
	 * Call to updates the image displayed
	 * @param the new image to display
	 */
	public void updateImage(BufferedImage image) {
		this.image = image;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}
		
}
