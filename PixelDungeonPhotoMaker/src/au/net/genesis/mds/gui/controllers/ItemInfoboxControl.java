package au.net.genesis.mds.gui.controllers;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.gui.MainGui;
import au.net.genesis.mds.gui.OptionsPanel;
import au.net.genesis.mds.gui.tabs.SliderLine;
import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.gui.tabs.TabAssetSelector.SelectorListener;
import au.net.genesis.mds.gui.tabs.TabBackgroundSelect;
import au.net.genesis.mds.gui.tabs.TabBackgroundSelect.BackgroundListener;
import au.net.genesis.mds.imageEditors.InfoboxCreator;


public class ItemInfoboxControl extends TabControl implements ActionListener, SelectorListener, ChangeListener, BackgroundListener {
	
	private InfoboxCreator ic;
	private TabAssetSelector assetSelector;
	private TabBackgroundSelect backgroundSelect;
	private SliderLine scaleSlider, shadowSlider, shadowOpacitySlider;
	
	public ItemInfoboxControl(OptionsPanel optionsPanel) {
		super(optionsPanel);
		ic = new InfoboxCreator()
			.setAsset(AssetFinder.getDungeonFile("items.png"))
			.setOutputFile(getOutputFile());
		scaleSlider = new SliderLine("Item Scale").initTo(1, 24, ic.getItemScale());
		scaleSlider.getSlider().addChangeListener(this);
		shadowSlider = new SliderLine("Shadow Size").initTo(0, 32, ic.getShadowRadius());
		shadowSlider.getSlider().addChangeListener(this);
		shadowOpacitySlider = new SliderLine("Shadow ocupacity").initTo(0, 100, (int)(ic.getShadowOpacity()*100));
		shadowOpacitySlider.getSlider().addChangeListener(this);
		assetSelector = new TabAssetSelector();
		assetSelector.addSelectorListener(this);
		backgroundSelect = new TabBackgroundSelect();
		backgroundSelect.addListener(this);
	}

	@Override
	public void configureAssetTab(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(assetSelector);
		
	}

	@Override
	public void configureOptionTab(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(scaleSlider);
		panel.add(shadowSlider);
		panel.add(shadowOpacitySlider);
		panel.add(backgroundSelect);
		
		panel.add(Box.createVerticalGlue());
		
	}

	@Override
	public void selectionChange(Rectangle selection) {
		ic.setSelection(selection);
		refreshPreview();
	}

	@Override
	public void assetChange(File file) {
		ic.setAsset(file);
		refreshPreview();
		
	}

	@Override
	public File createImage() {
		return ic.getImage();
		
	}
	
	@Override
	public String getName() {
		return "Item Infobox";
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == scaleSlider.getSlider()) {
			ic.setItemScale(scaleSlider.getSlider().getValue());
			refreshPreview();
			return;
		}
		if (e.getSource() == shadowSlider || e.getSource() == shadowOpacitySlider) {
			int size = shadowSlider.getSlider().getValue();
			int opacity = shadowOpacitySlider.getSlider().getValue(); 
			ic.configureShadow(size, ((float)opacity)/100F);
			refreshPreview();
		}
	}

	@Override
	public void backgroundChange(InfoboxBack back) {
		ic.setBackground(back);
		refreshPreview();
	}

	@Override
	public JButton getMenuButton() {
		if (menuButon == null) {
			menuButon = MainGui.createButton("Item Infobox",AssetFinder.getImageFile("exampleitem.png"));
			menuButon.addActionListener(this);
		}
		return super.getMenuButton();
	}
	
	public TabAssetSelector getAssetSelector() {
		return assetSelector;
	}
	public InfoboxCreator getInfoboxCreator() {
		return ic;
	}

	
}
