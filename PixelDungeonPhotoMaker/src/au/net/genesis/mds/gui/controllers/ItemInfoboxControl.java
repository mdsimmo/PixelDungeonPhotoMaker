package au.net.genesis.mds.gui.controllers;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import au.net.genesis.mds.assets.AssetLoader;
import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.gui.PreviewPanel;
import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.gui.tabs.TabAssetSelector.SelectorListener;
import au.net.genesis.mds.gui.tabs.TabBackgroundSelect;
import au.net.genesis.mds.gui.tabs.TabBackgroundSelect.BackgroundListener;
import au.net.genesis.mds.imageEditors.InfoboxCreator;


public class ItemInfoboxControl implements TabControl, ActionListener, SelectorListener, ChangeListener, BackgroundListener {
	
	protected InfoboxCreator ic;
	private PreviewPanel preview;
	protected TabAssetSelector assetSelector;
	private TabBackgroundSelect backgroundSelect;
	private JSlider scaleSlider = new JSlider(1, 24, 14);
	private JLabel scaleLabel = new JLabel(Integer.toString(scaleSlider.getValue()));
	private JSlider shadowSlider = new JSlider(1, 32, 8);
	private JLabel shadowLabel = new JLabel(Integer.toString(shadowSlider.getValue()));
	private JSlider shadowOpacitySlider = new JSlider(1, 100, 80);
	private JLabel shadowOpacityLabel = new JLabel(Integer.toString(shadowOpacitySlider.getValue()));
	
	public ItemInfoboxControl() {
		ic = new InfoboxCreator()
			.setAsset(AssetLoader.getDungeonPath("items.png"));
		assetSelector = new TabAssetSelector();
		assetSelector.addSelectorListener(this);
		backgroundSelect = new TabBackgroundSelect();
		backgroundSelect.addListener(this);
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
	public void refreshPreview() {
		if (preview != null) {
			preview.updateImage(getImage());
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

	
}
