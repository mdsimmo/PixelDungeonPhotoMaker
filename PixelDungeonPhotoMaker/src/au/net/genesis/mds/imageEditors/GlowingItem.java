package au.net.genesis.mds.imageEditors;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.imageio.ImageIO;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.helpers.GifSequenceWriter;

public class GlowingItem {

	private BufferedImage base;
	private File output = AssetFinder.getTempFile("");
	private Color color = Color.WHITE;
		
	
	public File createItem() {

		// fill translucent pixels with wiki's back color
		BufferedImage tempItem = new BufferedImage(base.getWidth(),
				base.getHeight(), base.getType());
		Graphics tempg = tempItem.getGraphics();
		tempg.setColor(new Color(71, 70, 70));
		for (int i = 0; i < base.getWidth(); i++) {
			for (int j = 0; j < base.getHeight(); j++) {
				int alpha = (base.getRGB(i, j) >> 24) & 0xFF;
				if (alpha != 0) {
					tempg.fillRect(i, j, 1, 1);
				}
			}
		}
		tempg.drawImage(base, 0, 0, null);
		base = tempItem;
		
		// create the glow to overlay
		BufferedImage glow = new BufferedImage(base.getWidth(), base.getHeight(), base.getType());
		Graphics2D g = (Graphics2D) glow.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, base.getWidth(), base.getHeight());
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
		g.drawImage(base, 0, 0, null);
		
		// create the final images
		BufferedImage out[] = new BufferedImage[30];
		for (int i = 0; i < out.length; i++) {
			// create basic item image
			out[i] = new BufferedImage(base.getWidth(), base.getHeight(), base.getType());
			Graphics2D g2 = (Graphics2D) out[i].getGraphics();
			g2.drawImage(base, 0, 0, null);
			
			// color the image
			float alpha = ((float)Math.sin((float)i*2F*Math.PI/out.length)+2F)/5F;
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2.setComposite(ac);
			g2.drawImage(glow, 0, 0, null);
			
			//save the image
			try {
				ImageIO.write(out[i], "png", new File(output.getPath() + "/glow" + intToString(i, 3) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		// compile the images into a gif
		File gifFile = new File(output.getPath() + "/glow.gif");
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.matches("glow\\d+\\.png")) 
					return true;
				else
					return false;
			}
		};
		
		GifSequenceWriter.createGif(output , filter, gifFile, 100, true, true);
		
		// return the file of the gif
		return gifFile;
	}
	
	static String intToString(int num, int digits) {
	    assert digits > 0 : "Invalid number of digits";

	    // create variable length array of zeros
	    char[] zeros = new char[digits];
	    Arrays.fill(zeros, '0');
	    // format number as String
	    DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

	    return df.format(num);
	}
	
	public GlowingItem setColor(Color color) {
		this.color = color;
		return this;
	}
	
	public Color getColor() {
		return color;
	}
	
	public GlowingItem setImage(BufferedImage image) {
		base = image;
		return this;
	}
}
