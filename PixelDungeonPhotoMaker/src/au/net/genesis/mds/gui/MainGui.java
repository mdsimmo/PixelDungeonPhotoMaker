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

import au.net.genesis.mds.assets.AssetFinder;
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
	public static Logger logger = new Logger();
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
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(previewPanel);
		right.add(logger);
		logger.log("Welcome to the Pixel Dungeon Photo Maker!");
		logger.log("");
		container.add(right);
		container.add(Box.createHorizontalGlue());
		optionPanel.refresh();
		this.pack();
	}

	/**
	 * Creates a button with text and an image
	 * @param text The button's text
	 * @param image The image's file
	 * @return the created button
	 */
	public static JButton createButton(String text, File image) {
		BufferedImage img = AssetFinder.loadImage(image);
		float scale = 64 / (float)img.getWidth();
		img = GraphicHelper.scaleImage(img, scale, scale);
		ImageIcon icon = new ImageIcon(img);
		JButton button = new JButton(text, icon);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setFont(new Font("Arial", Font.PLAIN, 10));
		return button;
	}

}
