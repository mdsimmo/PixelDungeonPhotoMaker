package au.net.genesis.mds.gui.controllers;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.gui.MainGui;
import au.net.genesis.mds.gui.OptionsPanel;
import au.net.genesis.mds.imageEditors.GlowingItem;

public class GlowingControl extends BasicImageControl {

	private GlowingItem gi;
	private JColorChooser colorChooser;
	
	public GlowingControl(OptionsPanel optionsPanel) {
		super(optionsPanel);
		gi = new GlowingItem();
		colorChooser = new JColorChooser(gi.getColor());
		assetSelector.setSelection(new Rectangle(16*3, 16*3, 16, 16));
		refreshPreview();
	}

	@Override
	public File createImage() {
		File f = super.createImage();
		BufferedImage item = AssetFinder.loadImage(f);
		gi.setImage(item);
		return gi.createItem();
	}
	
	@Override
	public void configureOptionTab(JPanel panel) {
		super.configureOptionTab(panel);
		JScrollPane scrollPane = new JScrollPane(colorChooser);
		colorChooser.getSelectionModel().addChangeListener(this);;
		colorChooser.setPreviewPanel(new JPanel());
		panel.add(scrollPane);
	}
	
	@Override
	public JButton getMenuButton() {
		if (menuButon == null) {
			menuButon = MainGui.createButton("Glowing item", AssetFinder.getImageFile("exampleglow.gif"));
			menuButon.addActionListener(this);
		}
		return super.getMenuButton();
	}
	
	@Override
	public String getName() {
		return "Glowing Item";
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		super.stateChanged(e);
		if (colorChooser != null && e.getSource() == colorChooser.getSelectionModel()) {
			gi.setColor(colorChooser.getColor());
			refreshPreview();
		}
		
	}

}
