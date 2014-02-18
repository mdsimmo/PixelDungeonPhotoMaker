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
import javax.security.auth.login.LoginException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import au.net.genesis.mds.PhotoMaker;
import au.net.genesis.mds.gui.controllers.EnemyInfoboxControl;
import au.net.genesis.mds.gui.controllers.ItemInfoboxControl;
import au.net.genesis.mds.gui.controllers.TabControl;
import au.net.genesis.mds.helpers.GraphicHelper;

/**
 * Creates a option panel to create Pixel Dungeon Wiki Assets
 * @author mdsimmo
 *
 */
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
	
	private JTextField usernameBox = new JTextField(12);
	private JTextField passwordBox = new JPasswordField(12);
	private JButton uploadButton = new JButton("Upload");
	private JTextField imagenameBox = new JTextField(12);
	
	/**
	 * 
	 * @param preview The preview window to link updates to
	 */
	public OptionsPanel(PreviewPanel preview) {
		// create the tabbed area
		tabs = new JTabbedPane();
		tabs.setPreferredSize(new Dimension(256,256));
		tabs.addTab("Pic Type", picType);
		tabs.addTab("Asset", assetSelector);
		tabs.addTab("Options", optionPanel);
		tabs.addTab("Upload", uploader);
		this.add(tabs);
		
		// create the picture controllers
		iic.setPreviewPanel(preview);
		eic.setPreviewPanel(preview);
		
		// create the buttons for the pic type tab
		itemButton = createButton("Item Infobox", new File(PhotoMaker.getResource("exampleItem.png")));
		itemButton.addActionListener(this);
		enemyButton = createButton("Enemy Infobox", new File(PhotoMaker.getResource("exampleEnemy.png")));
		enemyButton.addActionListener(this);
				
		refresh();
	}
	
	/**
	 * configures all the tabs to display the correct things<br>
	 * it is advised to call refresh() instead of this method
	 */
	private void configureTabs() {
		picType.removeAll();
		uploader.removeAll();
		assetSelector.removeAll();
		optionPanel.removeAll();
		configureTypeTab(picType);
		configureUploadTab(uploader);
		currentControl.configureTabAsset(assetSelector);
		currentControl.configureTabOptions(optionPanel);
	}
	
	/**
	 * Configures the pic type tab 
	 * @param panel The upload panel
	 */
	public void configureTypeTab(JPanel panel) {
		panel.setLayout(new FlowLayout());
		panel.add(itemButton);
		panel.add(enemyButton);
	}
	
	/**
	 * Configures the upload tab
	 * @param panel The upload panel
	 */
	public void configureUploadTab(JPanel panel) {
		panel.setLayout(new FlowLayout(SwingConstants.VERTICAL));
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
		userPanel.add(new JLabel("Username"));
		userPanel.add(usernameBox);
		panel.add(userPanel);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
		passwordPanel.add(new JLabel("Password"));
		passwordPanel.add(passwordBox);
		panel.add(passwordPanel);
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		namePanel.add(new JLabel("Image name"));
		namePanel.add(imagenameBox);
		panel.add(namePanel);
		
		uploadButton.addActionListener(this);
		panel.add(uploadButton);
	}
	
	/**
	 * Creates a button with text and an image
	 * @param text The button's text
	 * @param image The image's file
	 * @return the created button
	 */
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
			// set the controller to be the enemy infobox
			currentControl = eic;
			refresh();
			return;
		}
		if (e.getSource() == itemButton) {
			// set the controller to be the item infobox
			currentControl = iic;
			refresh();
			return;
		}
		if (e.getSource() == uploadButton) {
			// must save image before uploading it		
			File save = new File("output2/save.png");
			try {
				ImageIO.write(currentControl.getImage(), "png", save);
				PhotoMaker.wiki.login(usernameBox.getText(),passwordBox.getText());
				PhotoMaker.wiki.upload(save, imagenameBox.getText(), imagenameBox.getText(), "");
			} catch (LoginException e1) {
				System.err.println("couldn't login");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Refreshes all the tabs and reconfigures them and refreshes the preview.<br> 
	 * This should be called in preference to configureTabs()
	 */
	private void refresh() {
		configureTabs();
		currentControl.refreshPreview();
		repaint();
	}
	
}
