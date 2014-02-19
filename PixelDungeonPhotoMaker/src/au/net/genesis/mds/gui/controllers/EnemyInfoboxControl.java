package au.net.genesis.mds.gui.controllers;

import au.net.genesis.mds.assets.AssetLoader;

public class EnemyInfoboxControl extends ItemInfoboxControl {

	public EnemyInfoboxControl() {
		super();
		ic.setAsset(AssetLoader.getDungeonFile("rat.png"));
		assetSelector.setAssetFile(AssetLoader.getDungeonFile("rat.png"));
	}
	
}
