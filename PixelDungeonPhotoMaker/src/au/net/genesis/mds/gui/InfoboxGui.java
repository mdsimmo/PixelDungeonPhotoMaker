package au.net.genesis.mds.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import au.net.genesis.mds.gui.AssetSelector.SelectorListener;
import au.net.genesis.mds.gui.Preview.PreviewListener;
import au.net.genesis.mds.imageEditors.InfoboxCreator;

public class InfoboxGui implements Gui, PreviewListener, ChangeListener, SelectorListener {
	
	private SliderGui sliderPanel;
	private InfoboxCreator ic;
	private Preview preview;  
	private AssetSelector assetSelector;
	
	public InfoboxGui() {
		this.ic = new InfoboxCreator()
			.setAsset("assets/items.png");
		preview = new Preview(this);
		preview.setPreferredSize(new Dimension(256, 256));
		preview.process(true);
		assetSelector = new AssetSelector(this);
	}
	
	
	@Override
	public Gui setUp(JFrame f) {
		Container c = f.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(assetSelector);
		sliderPanel = new SliderGui("Image scale")
			.setRange(1, 20, 14);
		sliderPanel.getSlider().addChangeListener(this);
		c.add(sliderPanel);
		c.add(preview);
		return this;
	}

	@Override
	public BufferedImage getImage() {
		return ic.getImage();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == sliderPanel.getSlider()) {
			ic.setItemScale(((JSlider) e.getSource()).getValue());
		}
		
	}
	
	public void update() {
		ic.setSelection(assetSelector.getSelection());
	}

}
