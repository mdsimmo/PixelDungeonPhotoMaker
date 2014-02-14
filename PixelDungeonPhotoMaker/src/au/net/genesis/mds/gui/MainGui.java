package au.net.genesis.mds.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import au.net.genesis.mds.PhotoMaker;
import au.net.genesis.mds.helpers.GraphicHelper;

public class MainGui extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private Container c;
	
	public MainGui() {
		setSize(640, 480);
		
		c = this.getContentPane();
		c.setLayout(new FlowLayout());
	}
	
	public MainGui setContent(Gui content) {
		c.removeAll();
		content.setUp(this);
		this.revalidate();
		this.repaint();
		return this;
	}
	
	public static JButton creatButton(String text, String image) {
		BufferedImage loadedImg = null;
		try {
			loadedImg = ImageIO.read(new File(PhotoMaker.getResource(image)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadedImg = GraphicHelper.scaleImage(loadedImg, 0.5, 0.5);
		ImageIcon img = new ImageIcon(loadedImg);
		JButton button = new JButton(text, img);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		Font f = new Font("Arial", Font.BOLD, 15);
		button.setFont(f);
		return button;
	}

}
