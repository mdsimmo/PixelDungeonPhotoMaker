package au.net.genesis.mds.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import au.net.genesis.mds.gui.PreviewPanel;
import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.gui.tabs.TabAssetSelector.SelectorListener;
import au.net.genesis.mds.imageEditors.InfoboxCreator;


public class ItemInfoboxControl implements TabControl, ActionListener, SelectorListener {
	
	private InfoboxCreator ic;
	private ArrayList<PreviewPanel> previews = new ArrayList<PreviewPanel>();
	private TabAssetSelector assetSelector;
	
	public ItemInfoboxControl() {
		ic = new InfoboxCreator()
			.setAsset("assets/items.png");
		assetSelector = new TabAssetSelector();
		assetSelector.addSelectorListener(this);
	}

	@Override
	public void addPreviewPanel(PreviewPanel preview) {
		previews.add(preview);
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
		System.out.print("hi");
		for (PreviewPanel preview : previews) {
			preview.updateImage(ic.getImage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		refreshPreview();
		
	}

	@Override
	public void selectionChange() {
		ic.setSelection(assetSelector.getSelection());
		refreshPreview();
	}

	
	
	
	
}
