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
import javax.swing.JFileChooser;
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
		 * Called when the selected asset changes
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
			updateImage();
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
			notifySelectionChange();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == zoomIn) {
				this.scale *= 2;
			}
			if (e.getSource() == zoomOut) {
				this.scale *= 0.5;
			}
			if (e.getSource() == fileButton) {
				JFileChooser fc = new JFileChooser("assets");
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileName = fc.getSelectedFile().toString();
					updateImage();
					notifyAssetChange();
				}
			}
			drawImage = GraphicHelper.scaleImage(asset, scale, scale);
			scroller.getVerticalScrollBar().setValue(ystart);
			this.setPreferredSize(new Dimension(drawImage.getWidth(), drawImage.getHeight()));
			this.revalidate();
			repaint();
		}
		
		public void updateImage() {
			try {
				drawImage = asset = ImageIO.read(new File(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static final long serialVersionUID = 1L;
	private JScrollPane scroller = new JScrollPane();
	private JButton zoomIn = new JButton("+"), zoomOut = new JButton("-");
	private JButton fileButton = new JButton("Select file");
	private ArrayList<SelectorListener> listeners = new ArrayList<TabAssetSelector.SelectorListener>();
	private String fileName = "assets/items.png";
	private SelectorPanel selector = new SelectorPanel(this);
	
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
		buttonPanel.add(fileButton);
		buttonPanel.add(zoomIn);
		buttonPanel.add(zoomOut);
		this.add(buttonPanel);
		fileButton.addActionListener(selector);
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
	 * Add to the listeners that will be informed of any selection changes 
	 * @param listener
	 */
	public void addSelectorListener(SelectorListener listener) {
		this.listeners.add(listener);
	}
	
	public void setAssetFile(String file) {
		this.fileName = file;
		notifyAssetChange();
		selector.updateImage();
	}
	private void notifyAssetChange() {
		for (SelectorListener l : listeners) {
			l.assetChange(fileName);
		}
	}
	private void notifySelectionChange() {
		for (SelectorListener l : listeners) {
			l.selectionChange(getSelection());
		}
	}
	
}
