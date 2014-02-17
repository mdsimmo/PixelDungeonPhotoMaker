package au.net.genesis.mds.gui.controllers;

import javax.swing.JButton;
import javax.swing.JPanel;

import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.imageEditors.InfoboxCreator;


public class InfoboxControl {
	
	private InfoboxCreator ic;
	
	public InfoboxControl() {
		ic = new InfoboxCreator()
			.setAsset("assets/items.png");
	}

	public void configureTypeGui(JPanel type) {
		type.add(new JButton("Hello"));
	}
	public void configureAssetGui(JPanel asset) {
		asset.add(new TabAssetSelector());
	}
	public void configureOptionGui(JPanel option) {

	}
	public void configureUploadGui(JPanel upload) {
	}
	public void configureGuis(JPanel type, JPanel asset,
			JPanel option, JPanel upload) {
		configureTypeGui(type);
		configureAssetGui(asset);
		configureOptionGui(option);
		configureUploadGui(upload);
		
	}
	
	
	
}
