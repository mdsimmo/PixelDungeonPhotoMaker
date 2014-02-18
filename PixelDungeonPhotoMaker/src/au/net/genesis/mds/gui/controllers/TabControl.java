package au.net.genesis.mds.gui.controllers;

import javax.swing.JPanel;

import au.net.genesis.mds.gui.PreviewPanel;

public interface TabControl {

	public void addPreviewPanel(PreviewPanel preview);
	public void configureTabAsset(JPanel panel);
	public void configureTabOptions(JPanel panel);
	public void refreshPreview();
	
}
