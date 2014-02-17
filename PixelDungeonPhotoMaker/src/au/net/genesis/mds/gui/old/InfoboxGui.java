/*package au.net.genesis.mds.gui.old;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import au.net.genesis.mds.PhotoMaker;
import au.net.genesis.mds.gui.Preview;
import au.net.genesis.mds.gui.Preview.PreviewListener;
import au.net.genesis.mds.gui.optiontabs.AssetSelector;
import au.net.genesis.mds.gui.optiontabs.OptionTab;
import au.net.genesis.mds.gui.optiontabs.AssetSelector.SelectorListener;
import au.net.genesis.mds.imageEditors.InfoboxCreator;

public class InfoboxGui implements OptionTab, ActionListener, PreviewListener, ChangeListener, SelectorListener {
	
	private SliderGui sliderPanel;
	private InfoboxCreator ic;
	private Preview preview;  
	private AssetSelector assetSelector;
	private JTextField usernameBox;
	private JPasswordField passwordBox;
	private JButton uploadButton;
	private JTextField imageName;
	
	
	public InfoboxGui() {
		this.ic = new InfoboxCreator()
			.setAsset("assets/items.png");
		preview = new Preview(this);
		preview.setPreferredSize(new Dimension(256, 256));
		preview.process(true);
		assetSelector = new AssetSelector(this);
	}
	
	
	public OptionTab setUp(JFrame f) {
		Container c = f.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(assetSelector);
		sliderPanel = new SliderGui("Image scale")
			.setRange(1, 20, 14);
		sliderPanel.getSlider().addChangeListener(this);
		c.add(sliderPanel);
		c.add(preview);
		JSeparator seperator = new JSeparator(SwingConstants.HORIZONTAL);
		seperator.setPreferredSize(new Dimension (256,16));
		c.add(seperator);
		
		usernameBox = new JTextField(16);
		passwordBox = new JPasswordField(16);
		uploadButton = new JButton("Upload");
		uploadButton.addActionListener(this);
		imageName = new JTextField(16);
		c.add(usernameBox);
		c.add(passwordBox);
		c.add(imageName);
		c.add(uploadButton);
		return this;
	}

	@Override
	public BufferedImage getImage() {
		return ic.getImage();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == sliderPanel.getSlider()) {
			ic.setItemScale(((JSlider) e.getSource()).getValue());
		}
		
	}
	
	public void update() {
		ic.setSelection(assetSelector.getSelection());
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == uploadButton) {
			try {
				ImageIO.write(ic.getImage(), "png", new File("output2/save.png"));
				String password = passwordBox.getPassword().toString();
				PhotoMaker.wiki.login(usernameBox.getText(), password);
				password = "removed";
				PhotoMaker.wiki.upload(new File("output2/save.png"), imageName.getText(), "A testing image", "testing image upload");
			} catch (LoginException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}*/
