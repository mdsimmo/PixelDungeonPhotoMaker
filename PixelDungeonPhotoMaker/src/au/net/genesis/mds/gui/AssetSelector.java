package au.net.genesis.mds.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import au.net.genesis.mds.helpers.GraphicHelper;

public class AssetSelector extends JPanel {

	private class SelectorPanel extends JPanel implements MouseListener, ActionListener {
		private static final long serialVersionUID = 1L;
		private BufferedImage asset;
		private BufferedImage drawImage;
		private int xstart = 0, ystart = 0, yend = 16, xend = 16;
		private float scale = 1;
		
		public SelectorPanel() {
			try {
				drawImage = asset = ImageIO.read(new File("assets/items.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.addMouseListener(this);
			this.setPreferredSize(new Dimension(asset.getWidth(), asset.getHeight()));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(drawImage, 0, 0, null);
			g.drawRect((int)(xstart*scale), (int)(ystart*scale), (int)((xend-xstart)*scale), (int)((yend-ystart)*scale));
			System.out.print("paint");
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			xstart = e.getX();
			ystart = e.getY();
			repaint();
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			xend = e.getX();
			yend = e.getY();
			listener.update();
			repaint();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == zoomIn) {
				this.scale *= 2;
			}
			if (e.getSource() == zoomOut) {
				this.scale *= 0.5;
			}
			drawImage = GraphicHelper.scaleImage(asset, scale, scale);
			repaint();			
		}
	}
	
	public interface SelectorListener {
		public void update();
	}
	
	private static final long serialVersionUID = 1L;
	private JScrollPane scroller = new JScrollPane();
	private SelectorPanel selector = new SelectorPanel();
	private JButton zoomIn = new JButton("+"), zoomOut = new JButton("-");
	private SelectorListener listener;
	
	public AssetSelector(SelectorListener listener) {
		this.listener = listener;
		scroller.setPreferredSize(new Dimension(128, 128));
		scroller.setViewportView(selector);
		this.add(scroller);
		this.add(zoomIn);
		this.add(zoomOut);
		zoomIn.addActionListener(selector);
		zoomOut.addActionListener(selector);
	}
	
	public Rectangle getSelection() {
		return new Rectangle(selector.xstart, selector.ystart, selector.xend-selector.xstart, selector.yend-selector.ystart);

	}

}
