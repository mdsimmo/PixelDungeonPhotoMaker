package au.net.genesis.mds.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import au.net.genesis.mds.gui.controllers.InfoboxControl;


public class OptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static InfoboxControl ic = new InfoboxControl();
	private JTabbedPane tabs;
	private JPanel picType = new JPanel();
	private JPanel assetSelector = new JPanel();
	private JPanel optionPanel = new JPanel();
	private JPanel uploader = new JPanel();
	
	public OptionsPanel(PreviewPanel previewPanel) {
		// create the tabbed area
		tabs = new JTabbedPane();
		tabs.setPreferredSize(new Dimension(256,512));
		tabs.addTab("Pic Type", picType);
		tabs.addTab("Asset", assetSelector);
		tabs.addTab("Options", optionPanel);
		tabs.addTab("Upload", uploader);
		this.add(tabs);
		
		ic.configureGuis(picType, assetSelector, optionPanel, uploader);
	}
	
	public JPanel getTypePanel() {
		return picType;
	}
	public JPanel getAssetSelectorPanel() {
		return assetSelector;
	}
	public JPanel getOptionPanel() {
		return optionPanel;
	}
	public JPanel getUploaderPanel() {
		return uploader;
	}
	
}
