package au.net.genesis.mds.gui;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import au.net.genesis.mds.assets.AssetLoader;
import au.net.genesis.mds.helpers.GraphicHelper;

/**
 * Creates the gui that can be used to create images
 * for the Pixel Dungeon Wiki. <br>
 * Be warned: this gui is rather hacked together but it works (mostly)
 * 
 * @author mdsimmo
 *
 */
public class MainGui extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private PreviewPanel previewPanel = new PreviewPanel();
	private JPanel container = new JPanel();
	private OptionsPanel optionPanel = new OptionsPanel(previewPanel);
	
	/**
	 * Initializes the gui.
	 * 
	 */
	public MainGui() {
		getContentPane().add(container);
		container.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		container.add(optionPanel);
		container.add(new JSeparator(SwingConstants.VERTICAL));
		container.add(previewPanel);
		container.add(Box.createHorizontalGlue());
		this.pack();
	}

	/**
	 * Creates a button with text and an image
	 * @param text The button's text
	 * @param image The image's file
	 * @return the created button
	 */
	public static JButton createButton(String text, File image) {
		BufferedImage img = AssetLoader.loadImage(image);
		img = GraphicHelper.scaleImage(img, 0.25, 0.25);
		ImageIcon icon = new ImageIcon(img);
		JButton button = new JButton(text, icon);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setFont(new Font("Arial", Font.PLAIN, 10));
		return button;
	}

}
