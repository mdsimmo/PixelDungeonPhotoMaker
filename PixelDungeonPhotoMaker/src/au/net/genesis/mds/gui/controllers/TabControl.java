package au.net.genesis.mds.gui.controllers;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import au.net.genesis.mds.gui.PreviewPanel;

public interface TabControl {

	public void setPreviewPanel(PreviewPanel preview);
	public void configureTabAsset(JPanel panel);
	public void configureTabOptions(JPanel panel);
	public void refreshPreview();
	public BufferedImage getImage();
	
}
