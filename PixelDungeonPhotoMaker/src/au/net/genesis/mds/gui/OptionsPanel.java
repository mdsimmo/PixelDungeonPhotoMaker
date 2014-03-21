package au.net.genesis.mds.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import au.net.genesis.mds.gui.controllers.BasicImageControl;
import au.net.genesis.mds.gui.controllers.EnemyInfoboxControl;
import au.net.genesis.mds.gui.controllers.ItemInfoboxControl;
import au.net.genesis.mds.gui.controllers.ParticleControl;
import au.net.genesis.mds.gui.controllers.TabControl;

/**
 * Creates a option panel to create Pixel Dungeon Wiki Assets
 * @author mdsimmo
 *
 */
public class OptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabs;
	private static JPanel picTypeTab = new JPanel();
	private static JPanel assetTab = new JPanel();
	private static JPanel optionTab = new JPanel();
	private static JPanel uploadTab = new JPanel();
	
	private ArrayList<TabControl> tabControls = new ArrayList<TabControl>();
	private TabControl currentControl;
	
	/**
	 * 
	 * @param preview The preview window to link updates to
	 */
	public OptionsPanel(PreviewPanel preview) {
		// create the tabbed area
		tabs = new JTabbedPane();
		tabs.setPreferredSize(new Dimension(256,512));
		tabs.addTab("Pic Type", picTypeTab);
		tabs.addTab("Asset", assetTab);
		tabs.addTab("Options", optionTab);
		tabs.addTab("Save", uploadTab);
		this.add(tabs);
		
		// create the picture controllers
		tabControls.add(new ItemInfoboxControl(this));
		tabControls.add(new EnemyInfoboxControl(this));
		tabControls.add(new BasicImageControl(this));
		tabControls.add(new ParticleControl(this));
		for (TabControl control : tabControls) {
			control.setPreviewPanel(preview);
		}
		currentControl = tabControls.get(0);
	}
	
	/**
	 * Configures the pic type tab 
	 * @param panel The upload panel
	 */
	private void configureTypeTab(JPanel panel) {
		panel.setLayout(new FlowLayout());
		for (TabControl control : tabControls) {
			panel.add(control.getMenuButton());
		}
	}
	
	/**
	 * Refreshes all the tabs and reconfigures them and refreshes the preview.
	 */
	public void refresh() {
		picTypeTab.removeAll();
		uploadTab.removeAll();
		assetTab.removeAll();
		optionTab.removeAll();
		configureTypeTab(picTypeTab);
		currentControl.configureAssetTab(assetTab);
		currentControl.configureOptionTab(optionTab);
		currentControl.configureUploadTab(uploadTab);
		currentControl.refreshPreview();
		repaint();
	}

	public OptionsPanel setCurrentControl(TabControl tabControl) {
		currentControl = tabControl;
		refresh();
		return this;
	}
	
}
