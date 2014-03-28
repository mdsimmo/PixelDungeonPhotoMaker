package au.net.genesis.mds.gui.controllers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.security.auth.login.LoginException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import au.net.genesis.mds.PhotoMaker;
import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.gui.MainGui;
import au.net.genesis.mds.gui.OptionsPanel;
import au.net.genesis.mds.gui.PreviewPanel;
import au.net.genesis.mds.gui.tabs.InputLine;
import au.net.genesis.mds.gui.tabs.InputLine.Type;

public abstract class TabControl implements ActionListener {
	
	private InputLine username, password, imageName;
	private JButton uploadButton = new JButton("Upload to wiki");
	private JButton saveButton = new JButton("Save to computer");
	private File previousSave;
	private JPanel configueredUpload;
	private OptionsPanel optionsPanel;
	protected JButton menuButon;
	private PreviewPanel preview;
	private boolean loggedIn = false;
	
	public abstract void configureAssetTab(JPanel panel);
	public abstract void configureOptionTab(JPanel panel);
	public abstract File createImage();
	public abstract String getName();
	
	public File getOutputFile() {
		return AssetFinder.getTempFile("save.png");
	}
	
	public File getImageFile() {
		return getOutputFile();
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
			menuButon = MainGui.createButton(getName(), AssetFinder.getImageFile("mark.png"));
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
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			imageName = new InputLine("Image name");
			panel.add(imageName);
			
			username = new InputLine("Username");
			panel.add(username);
			
			password = new InputLine("Password", Type.PASSWORD);
			panel.add(password);
			
			JPanel savePanel = new JPanel();
			savePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
			savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
			savePanel.add(uploadButton);
			uploadButton.addActionListener(this);
			savePanel.add(saveButton);
			saveButton.addActionListener(this);
			panel.add(savePanel);
			
			panel.add(Box.createVerticalGlue());
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
			final File save = getImageFile();
			Thread upload = new Thread() {
				public void run() {
					try {
						if (!loggedIn) {
							loggedIn = true;
							MainGui.logger.log("Logging in to the wiki...");
							PhotoMaker.wiki.login(username.getText(), password.getText());
						}
						MainGui.logger.log("Upploading image...");
						PhotoMaker.wiki.upload(save, imageName.getText(), "\n", " ");
						MainGui.logger.log("Image successfully uploaded :D");
					} catch (LoginException e1) {
						MainGui.logger.log("Error with login!!!");
						e1.printStackTrace();
						return;
					} catch (IOException e1) {
						MainGui.logger.log("Failed to upload image");
						e1.printStackTrace();
						return;
					}
				}
			};
			upload.start();
		}
		if (e.getSource() == saveButton) {
			JFileChooser fc = new JFileChooser(previousSave);
			int returned = fc.showSaveDialog(null);
			if (returned == JFileChooser.APPROVE_OPTION) {
				previousSave = fc.getSelectedFile();
				try {
					Files.copy(getImageFile().toPath(), previousSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
					MainGui.logger.log("Saved image");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
}
