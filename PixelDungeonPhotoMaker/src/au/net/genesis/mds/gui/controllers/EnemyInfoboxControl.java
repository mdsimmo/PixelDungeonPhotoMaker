package au.net.genesis.mds.gui.controllers;

import javax.swing.JButton;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.gui.MainGui;
import au.net.genesis.mds.gui.OptionsPanel;

public class EnemyInfoboxControl extends ItemInfoboxControl {

	public EnemyInfoboxControl(OptionsPanel optionsPanel) {
		super(optionsPanel);
		getInfoboxCreator().setAsset(AssetFinder.getDungeonFile("rat.png"));
		getAssetSelector().setAssetFile(AssetFinder.getDungeonFile("rat.png"));
		refreshPreview();
	}
	
	@Override
	public JButton getMenuButton() {
		if (menuButon == null) {
			menuButon = MainGui.createButton("Enemy Infobox",AssetFinder.getImageFile("exampleenemy.png"));
			menuButon.addActionListener(this);
		}
		return super.getMenuButton();
	}
	
	@Override
	public String getName() {
		return "Enemy Infobox";
	}
	
	
}
