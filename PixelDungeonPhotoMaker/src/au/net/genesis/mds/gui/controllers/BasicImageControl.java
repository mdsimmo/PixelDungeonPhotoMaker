package au.net.genesis.mds.gui.controllers;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.gui.MainGui;
import au.net.genesis.mds.gui.OptionsPanel;
import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.gui.tabs.TabAssetSelector.SelectorListener;
import au.net.genesis.mds.imageEditors.BasicImageCreator;

public class BasicImageControl extends TabControl implements SelectorListener, ChangeListener {

	protected BasicImageCreator bic;
	protected TabAssetSelector assetSelector;
	private JSlider scaleSlider = new JSlider(1,16,3);
	private JLabel scaleLabel = new JLabel(Integer.toString(scaleSlider.getValue()));
	
	public BasicImageControl(OptionsPanel optionsPanel) {
		super(optionsPanel);
		bic = new BasicImageCreator()
			.setAsset(AssetFinder.getDungeonFile("items.png"));
		assetSelector = new TabAssetSelector();
		assetSelector.addSelectorListener(this);
	}

	@Override
	public void configureAssetTab(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(assetSelector);
		
	}

	@Override
	public void configureOptionTab(JPanel panel) {
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
	public void selectionChange(Rectangle selection) {
		bic.setSelection(selection);
		refreshPreview();
	}

	@Override
	public void assetChange(File file) {
		bic.setAsset(file);
		refreshPreview();
		
	}

	@Override
	public File createImage() {
		return bic.getImage();
	}

	@Override
	public String getName() {
		return "Basic Image";
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == scaleSlider) {
			scaleLabel.setText(Integer.toString(scaleSlider.getValue()));
			bic.setItemScale(scaleSlider.getValue());
			refreshPreview();
		}
		
	}

	@Override
	public JButton getMenuButton() {
		if (menuButon == null) {
			menuButon = MainGui.createButton("Basic image",AssetFinder.getImageFile("well.png"));
			menuButon.addActionListener(this);
		}
		return super.getMenuButton();
	}	
}
