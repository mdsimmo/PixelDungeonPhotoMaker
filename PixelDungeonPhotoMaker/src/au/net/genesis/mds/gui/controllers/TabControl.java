package au.net.genesis.mds.gui.controllers;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import au.net.genesis.mds.PhotoMaker;
import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.gui.MainGui;
import au.net.genesis.mds.gui.OptionsPanel;
import au.net.genesis.mds.gui.PreviewPanel;

public abstract class TabControl implements ActionListener {
	
	protected JTextField usernameBox = new JTextField(12);
	protected JTextField passwordBox = new JPasswordField(12);
	protected JButton uploadButton = new JButton("Upload to wiki");
	protected JButton saveButton = new JButton("Save to computer");
	protected JTextField imagenameBox = new JTextField(12);
	protected OptionsPanel optionsPanel;
	protected JButton menuButon;
	private PreviewPanel preview;
	protected File outputFile = AssetFinder.getTempFile("save.png");
	private JPanel configueredUpload;
	
	public abstract void configureAssetTab(JPanel panel);
	public abstract void configureOptionTab(JPanel panel);
	public abstract File createImage();
	public abstract String getName();
	
	public File getImage() {
		return outputFile;
	}
	
	public void setPreviewPanel(PreviewPanel preview) {
		this.preview = preview;
	}
	
	public void refreshPreview() {
		if (preview != null) {
			preview.updateImage(this);
		}
	}
	
	public TabControl(OptionsPanel optionsPanel) {
		this.optionsPanel = optionsPanel;
	}
	
	public JButton getMenuButton() {
		if (menuButon == null) {
			menuButon = MainGui.createButton("Unkowen button",AssetFinder.getImageFile("mark.png"));
			menuButon.addActionListener(this);
		}
		return menuButon;
	}
	
	/**
	 * Configures the upload tab
	 * @param panel The upload panel
	 */
	public void configureUploadTab(JPanel panel) {
		if (configueredUpload == null) {
			panel.setLayout(new FlowLayout(SwingConstants.VERTICAL));
			
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
			namePanel.add(new JLabel("Image name"));
			namePanel.add(imagenameBox);
			panel.add(namePanel);
			
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
			
			JPanel savePanel = new JPanel();
			savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
			savePanel.add(uploadButton);
			uploadButton.addActionListener(this);
			savePanel.add(saveButton);
			saveButton.addActionListener(this);
			panel.add(savePanel);
		}
		panel = configueredUpload;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuButon) {
			// configure the tabs to be correct
			optionsPanel.setCurrentControl(this);
			return;
		}
		if (e.getSource() == uploadButton) {
			// Upload an image
			File save = getImage();
			try {
				MainGui.logger.log("Logging in to the wiki...");
				PhotoMaker.wiki.login(usernameBox.getText(),passwordBox.getText());
				MainGui.logger.log("Upploading image...");
				PhotoMaker.wiki.upload(save, imagenameBox.getText(), "description", "added photo");
				MainGui.logger.log("Image successfully uploaded :D");
			} catch (LoginException e1) {
				MainGui.logger.log("Error with login!!!");
			} catch (IOException e1) {
				MainGui.logger.log("Failed to upload image");
			}
		}
	}
	
}
