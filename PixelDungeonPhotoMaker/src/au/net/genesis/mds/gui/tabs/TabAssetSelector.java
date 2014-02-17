package au.net.genesis.mds.gui.tabs;

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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import au.net.genesis.mds.helpers.GraphicHelper;

public class TabAssetSelector extends JPanel{

	private static final long serialVersionUID = 1L;

	private class SelectorPanel extends JPanel implements MouseListener, ActionListener {
		private static final long serialVersionUID = 1L;
		private BufferedImage asset;
		private BufferedImage drawImage;
		private int xstart = 0, ystart = 0, yend = 16, xend = 16;
		private float scale = 1;
		
		public SelectorPanel(TabAssetSelector tabAssetSelector) {
			try {
				drawImage = asset = ImageIO.read(new File("assets/items.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.addMouseListener(this);
			this.setPreferredSize(new Dimension(drawImage.getWidth(), drawImage.getHeight()));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(drawImage, 0, 0, null);
			g.drawRect((int)(xstart*scale), (int)(ystart*scale), (int)((xend-xstart)*scale), (int)((yend-ystart)*scale));
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
			xstart = (int) (e.getX()/scale);
			ystart = (int) (e.getY()/scale);
			repaint();
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			xend = (int) (e.getX()/scale);
			yend = (int) (e.getY()/scale);
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
			scroller.getVerticalScrollBar().setValue(ystart);
			this.setPreferredSize(new Dimension(drawImage.getWidth(), drawImage.getHeight()));
			this.revalidate();
			repaint();
		}
	}
	
	private JScrollPane scroller = new JScrollPane();
	private SelectorPanel selector = new SelectorPanel(this);
	private JButton zoomIn = new JButton("+"), zoomOut = new JButton("-");
	
	public void configureTab(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		scroller.setViewportView(selector);
		panel.add(scroller);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(zoomIn);
		buttonPanel.add(zoomOut);
		panel.add(buttonPanel);
		zoomIn.addActionListener(selector);
		zoomOut.addActionListener(selector);
	}
	
	public Rectangle getSelection() {
		return new Rectangle(selector.xstart, selector.ystart, selector.xend-selector.xstart, selector.yend-selector.ystart);

	}

}
