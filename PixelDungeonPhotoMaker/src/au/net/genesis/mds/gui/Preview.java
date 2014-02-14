package au.net.genesis.mds.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Preview extends JPanel {

	private class Runner extends Thread {
			
		private BufferedImage image;
		private Preview preview;
		
		public Runner(Preview preview) {
			this.preview = preview;
			image = listener.getImage();
		}
		
		public void run() {
			while (process) {
				image = listener.getImage();
				preview.repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public BufferedImage getImage() {
			return image;
		}
	}
	
	public interface PreviewListener {
		public BufferedImage getImage();			
	}
	
	private static final long serialVersionUID = 1L;
	private PreviewListener listener;
	private boolean process = false;
	private Runner runner;
	
			
	public Preview(PreviewListener listener) {
		this.listener = listener;
		runner = new Runner(this);
	}
			
	
	public void process(boolean process) {
		this.process = process;
		if (this.process == true) {
			runner.start();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(runner.getImage(), 0, 0, null);
	}
		
}
