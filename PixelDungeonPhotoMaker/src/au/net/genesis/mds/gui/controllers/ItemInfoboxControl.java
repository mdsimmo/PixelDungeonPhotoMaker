package au.net.genesis.mds.gui.controllers;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import au.net.genesis.mds.gui.PreviewPanel;
import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.gui.tabs.TabAssetSelector.SelectorListener;
import au.net.genesis.mds.imageEditors.InfoboxCreator;


public class ItemInfoboxControl implements TabControl, ActionListener, SelectorListener {
	
	protected InfoboxCreator ic;
	protected PreviewPanel preview;
	protected TabAssetSelector assetSelector;
	
	public ItemInfoboxControl() {
		ic = new InfoboxCreator()
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void refreshPreview() {
		if (preview != null) {
			preview.updateImage(ic.getImage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		refreshPreview();
		
	}

	@Override
	public void selectionChange(Rectangle selection) {
		ic.setSelection(selection);
		refreshPreview();
	}

	@Override
	public void assetChange(String file) {
		ic.setAsset(file);
		refreshPreview();
		
	}

	@Override
	public BufferedImage getImage() {
		return ic.getImage();
	}

	
}
