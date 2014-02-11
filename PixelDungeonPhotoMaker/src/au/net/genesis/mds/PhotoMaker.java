package au.net.genesis.mds;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.wikipedia.Wiki;

import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.imageEditors.ItemCreator;

public class PhotoMaker {
	
	public static void main(String[] args) {
		ItemCreator ic = new ItemCreator();
		ic.setAsset(getResource("assets/items.png")).setBackground(InfoboxBack.HALLS);
		try {
			ImageIO.write(ic.getImage(), "png", new File(getResource("saved.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Wiki wiki = new Wiki("pixeldungeon.wikia.com", "");
	}
	
	/** 
	 * Gets the path to the resources <br>
	 * (mainly used so I can change where resources are stored)
	 * @param string
	 * @return the resource path
	 */
	public static String getResource(String string) {
		return "resources/" + string;
		
	}

}
