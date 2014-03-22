package au.net.genesis.mds.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.gui.controllers.TabControl;

public class PreviewPanel extends JPanel {
	
	private class UpdaterWorker implements Runnable {
		
		private TabControl control; 
		public UpdaterWorker(TabControl control) {
			this.control = control;
		}
		
		@Override
		public void run() {
			// quickly skip over if there's another image trying to process
			if (currentRunner == this) {
				try {
					MainGui.logger.log("Creating " + control.getName() + "...");
					remakeJLabel(loadIcon);
					File f = control.createImage();
					remakeJLabel(new ImageIcon(f.toURI().toURL()));
					MainGui.logger.log(control.getName() + " created.");
					repaint();
				} catch (IOException e) {
					e.printStackTrace();
					MainGui.logger.log("Something went wrong creating " + control.getName());
				}
			}
		}		
	}
	
	private static final long serialVersionUID = 1L;
	private ExecutorService exe;
	private UpdaterWorker currentRunner;
	private ImageIcon loadIcon;
			
	/**
	 * Creates a JPanel that can be used to display an image
	 * 
	 * @param image the initial image to display
	 */
	public PreviewPanel() {
		this.setPreferredSize(new Dimension(256,256));
		this.setLayout(new FlowLayout());
		exe = Executors.newSingleThreadExecutor();
		
		try {
			loadIcon = new ImageIcon(AssetFinder.getImageFile("loadanimation.gif").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Call to updates the image displayed
	 * @param control the TabControl to get the image from
	 */
	public void updateImage(TabControl control) {
		UpdaterWorker runner = new UpdaterWorker(control);
		currentRunner = runner;
		exe.execute(runner);
	}
	
	public void remakeJLabel(ImageIcon icon) {
		icon.getImage().flush();
		JLabel label = new JLabel(icon);
		label.setDoubleBuffered(true);
		label.invalidate();
		label.revalidate();
		
		removeAll();
		add(label);
		validate();
		repaint();
	}		
}
