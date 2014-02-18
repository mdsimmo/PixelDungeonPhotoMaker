package au.net.genesis.mds.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import au.net.genesis.mds.PhotoMaker;
import au.net.genesis.mds.gui.controllers.EnemyInfoboxControl;
import au.net.genesis.mds.gui.controllers.ItemInfoboxControl;
import au.net.genesis.mds.gui.controllers.TabControl;
import au.net.genesis.mds.helpers.GraphicHelper;


public class OptionsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabs;
	private JPanel picType = new JPanel();
	private JPanel assetSelector = new JPanel();
	private JPanel optionPanel = new JPanel();
	private JPanel uploader = new JPanel();
	
	private ItemInfoboxControl iic = new ItemInfoboxControl();
	private EnemyInfoboxControl eic = new EnemyInfoboxControl();
	private TabControl currentControl = iic;
	
	private JButton itemButton;
	private JButton enemyButton;
	
	public OptionsPanel(PreviewPanel preview) {
		// create the tabbed area
		tabs = new JTabbedPane();
		tabs.setPreferredSize(new Dimension(256,256));
		tabs.addTab("Pic Type", picType);
		tabs.addTab("Asset", assetSelector);
		tabs.addTab("Options", optionPanel);
		tabs.addTab("Upload", uploader);
		this.add(tabs);
		
		iic.addPreviewPanel(preview);
		eic.addPreviewPanel(preview);
		
		itemButton = createButton("Item Infobox", new File(PhotoMaker.getResource("exampleEnemy.png")));
		itemButton.addActionListener(this);
		enemyButton = createButton("Enemy Infobox", new File(PhotoMaker.getResource("exampleEnemy.png")));
		enemyButton.addActionListener(this);
		
		refresh();
	}
	
	public void configureTabs() {
		picType.removeAll();
		uploader.removeAll();
		assetSelector.removeAll();
		optionPanel.removeAll();
		configureTypeTab(picType);
		configureUploadTab(uploader);
		currentControl.configureTabAsset(assetSelector);
		currentControl.configureTabOptions(optionPanel);
	}
	
	public void configureTypeTab(JPanel panel) {
		panel.setLayout(new FlowLayout());
		panel.add(itemButton);
		panel.add(enemyButton);
	}
	public void configureUploadTab(JPanel panel) {
		
	}
	
	public JButton createButton(String text, File image) {
		ImageIcon icon;
		try {
			BufferedImage img = ImageIO.read(image);
			img = GraphicHelper.scaleImage(img, 0.25, 0.25);
			icon = new ImageIcon(img);
		} catch (IOException e) {
			icon = null;
			System.err.println("Could not load image: " + image.toString());
		}
		JButton button = new JButton(text, icon);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setFont(new Font("Arial", Font.PLAIN, 10));
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enemyButton) {
			currentControl = eic;
			refresh();
			return;
		}
		if (e.getSource() == itemButton) {
			currentControl = iic;
			refresh();
			return;
		}
		
	}
	private void refresh() {
		configureTabs();
		currentControl.refreshPreview();
		repaint();
	}
	
}
