package au.net.genesis.mds.gui.controllers;

import au.net.genesis.mds.assets.AssetLoader;

public class EnemyInfoboxControl extends ItemInfoboxControl {

	public EnemyInfoboxControl() {
		super();
		ic.setAsset(AssetLoader.getDungeonPath("rat.png"));
		assetSelector.setAssetFile(AssetLoader.getDungeonPath("rat.png"));
	}
	
}
