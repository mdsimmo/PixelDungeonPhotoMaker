package au.net.genesis.mds.gui.controllers;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
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
import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.gui.MainGui;
import au.net.genesis.mds.gui.OptionsPanel;
import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.gui.tabs.TabAssetSelector.SelectorListener;
import au.net.genesis.mds.gui.tabs.TabBackgroundSelect;
import au.net.genesis.mds.gui.tabs.TabBackgroundSelect.BackgroundListener;
import au.net.genesis.mds.imageEditors.InfoboxCreator;


public class ItemInfoboxControl extends TabControl implements ActionListener, SelectorListener, ChangeListener, BackgroundListener {
	
	private InfoboxCreator ic;
	private TabAssetSelector assetSelector;
	private TabBackgroundSelect backgroundSelect;
	private JSlider scaleSlider;
	private JLabel scaleLabel;
	private JSlider shadowSlider;
	private JLabel shadowLabel;
	private JSlider shadowOpacitySlider;
	private JLabel shadowOpacityLabel;
	
	public ItemInfoboxControl(OptionsPanel optionsPanel) {
		super(optionsPanel);
		ic = new InfoboxCreator()
			.setAsset(AssetFinder.getDungeonFile("items.png"))
			.setOutputFile(outputFile);
		scaleSlider = new JSlider(1, 24, ic.getItemScale());
		scaleLabel = new JLabel(Integer.toString(scaleSlider.getValue()));
		shadowSlider = new JSlider(0, 32, ic.getShadowRadius());
		shadowLabel = new JLabel(Integer.toString(shadowSlider.getValue()));
		shadowOpacitySlider = new JSlider(0, 100, (int)(ic.getShadowOpacity()*100));
		shadowOpacityLabel = new JLabel(Integer.toString(shadowOpacitySlider.getValue()));
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
		
		JPanel scalePanel = new JPanel();
		scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.X_AXIS));
		scalePanel.add(new JLabel("Item scale"));
		scaleSlider.addChangeListener(this);
		scalePanel.add(scaleSlider);
		scalePanel.add(scaleLabel);
		panel.add(scalePanel);
		
		JPanel shadowPanel = new JPanel();
		shadowPanel.setLayout(new BoxLayout(shadowPanel, BoxLayout.X_AXIS));
		shadowPanel.add(new JLabel("Shadow size"));
		shadowSlider.addChangeListener(this);
		shadowPanel.add(shadowSlider);
		shadowPanel.add(shadowLabel);
		panel.add(shadowPanel);
		
		JPanel shadowOpacityPanel = new JPanel();
		shadowOpacityPanel.setLayout(new BoxLayout(shadowOpacityPanel, BoxLayout.X_AXIS));
		shadowOpacityPanel.add(new JLabel("Shadow opacity"));
		shadowOpacitySlider.addChangeListener(this);
		shadowOpacityPanel.add(shadowOpacitySlider);
		shadowOpacityPanel.add(shadowOpacityLabel);
		panel.add(shadowOpacityPanel);
		
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
		if (e.getSource() == scaleSlider) {
			int scale = ((JSlider)e.getSource()).getValue();
			ic.setItemScale(scale);
			scaleLabel.setText(Integer.toString(scale));
			refreshPreview();
			return;
		}
		if (e.getSource() == shadowSlider || e.getSource() == shadowOpacitySlider) {
			int size = shadowSlider.getValue();
			int opacity = shadowOpacitySlider.getValue(); 
			ic.configureShadow(size, ((float)opacity)/100F);
			shadowLabel.setText(Integer.toString(size));
			shadowOpacityLabel.setText(Integer.toString(opacity));
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
