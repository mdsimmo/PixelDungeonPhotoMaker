package au.net.genesis.mds.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import au.net.genesis.mds.imageEditors.InfoboxCreator;

public class InfoboxGui implements Gui {
	
	private SliderGui sliderPanel;
	
	private class Preview extends JPanel implements GuiListener, ChangeListener {
		
		private static final long serialVersionUID = 1L;

		private class Runner extends Thread {
			
			private GuiListener guiListener;
			private BufferedImage image;
			
			public Runner(GuiListener guiListener) {
				this.guiListener = guiListener;
				image = ic.getImage();
			}
			
			public void run() {
				while (process) {
					image = ic.getImage();
					guiListener.GuiChange(null);
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
		
		private InfoboxCreator ic;
		private boolean process = false;
		private Runner runner;
				
		public Preview(InfoboxCreator ic) {
			this.ic = ic;
			runner = new Runner(this);
			setPreferredSize(new Dimension(265,256));
		}
				
		
		public void process(boolean process) {
			this.process = process;
			if (this.process == true) {
				runner.start();
			}
		}
		
		@Override
		public void GuiChange(Gui gui) {
			repaint();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(runner.getImage(), 0, 0, null);
		}


		@Override
		public void stateChanged(ChangeEvent e) {
			ic.setItemScale(((JSlider)e.getSource()).getValue());			
		}
			
	}
	
	private InfoboxCreator ic;
	private Preview preview;  
	
	public InfoboxGui() {
		this.ic = new InfoboxCreator()
			.setAsset("assets/items.png");
		 preview = new Preview(ic);
		 preview.process(true);
		
	}
	
	@Override
	public Gui setUp(JFrame f) {
		Container c = f.getContentPane();
		c.setLayout(new FlowLayout());
		sliderPanel = new SliderGui("Image scale")
			.setRange(1, 20, 14);
		sliderPanel.getSlider().addChangeListener(preview);
		c.add(sliderPanel);
		c.add(preview);
		return this;
	}

}
