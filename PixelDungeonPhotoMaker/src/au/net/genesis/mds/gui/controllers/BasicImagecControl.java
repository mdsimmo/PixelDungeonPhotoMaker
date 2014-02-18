package au.net.genesis.mds.gui.controllers;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import au.net.genesis.mds.gui.PreviewPanel;
import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.gui.tabs.TabAssetSelector.SelectorListener;
import au.net.genesis.mds.imageEditors.BasicImageCreator;

public class BasicImagecControl implements TabControl, SelectorListener, ChangeListener {

	protected BasicImageCreator bic;
	protected PreviewPanel preview;
	protected TabAssetSelector assetSelector;
	private JSlider scaleSlider = new JSlider(1,16,3);
	private JLabel scaleLabel = new JLabel(Integer.toString(scaleSlider.getValue()));
	
	public BasicImagecControl() {
		bic = new BasicImageCreator()
			.setAsset("assets/items.png");
		assetSelector = new TabAssetSelector();
		assetSelector.addSelectorListener(this);
	}

	@Override
	public void setPreviewPanel(PreviewPanel preview) {
		this.preview = preview;
	}

	@Override
	public void configureTabAsset(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(assetSelector);
		
	}

	@Override
	public void configureTabOptions(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel scalePanel = new JPanel();
		scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.X_AXIS));
		scalePanel.add(new JLabel("Item Scale"));
		scaleSlider.addChangeListener(this);
		scalePanel.add(scaleSlider);
		scalePanel.add(scaleLabel);
		panel.add(scalePanel);
		
		panel.add(Box.createVerticalGlue());
		
	}
	
	@Override
	public void refreshPreview() {
		if (preview != null) {
			preview.updateImage(getImage());
		}
	}

	@Override
	public void selectionChange(Rectangle selection) {
		bic.setSelection(selection);
		refreshPreview();
	}

	@Override
	public void assetChange(String file) {
		bic.setAsset(file);
		refreshPreview();
		
	}

	@Override
	public BufferedImage getImage() {
		return bic.getImage();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == scaleSlider) {
			scaleLabel.setText(Integer.toString(scaleSlider.getValue()));
			bic.setItemScale(scaleSlider.getValue());
			refreshPreview();
		}
		
	}
	
}
