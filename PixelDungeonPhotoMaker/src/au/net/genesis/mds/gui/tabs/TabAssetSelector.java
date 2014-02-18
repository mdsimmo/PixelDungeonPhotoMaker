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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import au.net.genesis.mds.helpers.GraphicHelper;

/**
 * A panel that lets the user select a specific part of a specific image.
 * 
 * @author mdsimmo
 *
 */
public class TabAssetSelector extends JPanel{

	/** 
	 * An interface used to inform the listener of any selection changes
	 */
	public interface SelectorListener {
		/**
		 * Called when the selection changes
		 * @param selection the selection being updated
		 */
		public void selectionChange(Rectangle selection);
		/**
		 * TODO Called when the selected asset changes
		 */
		public void assetChange(String file);
	}

	/**
	 * The panel which the selected asset is drawn onto.
	 *
	 */
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
			for (SelectorListener l : listeners) {
				l.selectionChange(getSelection());
			}
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
	
	private static final long serialVersionUID = 1L;
	private JScrollPane scroller = new JScrollPane();
	private SelectorPanel selector = new SelectorPanel(this);
	private JButton zoomIn = new JButton("+"), zoomOut = new JButton("-");
	private ArrayList<SelectorListener> listeners = new ArrayList<TabAssetSelector.SelectorListener>();
	
	/**
	 * Creates a asset selector. <br>
	 * It allows the user to choose an image and then specify a selection of that image.
	 */
	public TabAssetSelector() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		scroller.setViewportView(selector);
		this.add(scroller);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(zoomIn);
		buttonPanel.add(zoomOut);
		this.add(buttonPanel);
		zoomIn.addActionListener(selector);
		zoomOut.addActionListener(selector);
	}
	
	/**
	 * Gets the selection that the user has specified
	 * @return the selected area
	 */
	private Rectangle getSelection() {
		return new Rectangle(selector.xstart, selector.ystart, selector.xend-selector.xstart, selector.yend-selector.ystart);
	}
	/**
	 * Gets the asset that the user has specified
	 * @return the selected asset
	 */
	private String getAsset() {
		//TODO getAsset()
		return "";
	}
	
	/**
	 * Add to the listeners that will be informed of any selection changes 
	 * @param listener
	 */
	public void addSelectorListener(SelectorListener listener) {
		this.listeners.add(listener);
	}

}
