package au.net.genesis.mds;

import javax.swing.JFrame;

import org.wikipedia.Wiki;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.gui.MainGui;

public class PhotoMaker {

	public static Wiki wiki;

	public static void createGui() {
		MainGui mainGui = new MainGui();
		mainGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGui.setResizable(false);
		mainGui.setVisible(true);
	}

	public static void setUpWikibot() {
		wiki = new Wiki("pixeldungeon.wikia.com", "");
		wiki.setUsingCompressedRequests(false);
	}
	public static void checkDirectories() {
		if (!AssetFinder.getImageFile("").exists()) {
			AssetFinder.getImageFile("").mkdirs();
		}
		if (!AssetFinder.getDungeonFile("").exists()) {
			AssetFinder.getDungeonFile("").mkdirs();
			System.err.println("The game assets are missing!!!");
		}
		if (!AssetFinder.getTempFile("").exists()) {
			AssetFinder.getTempFile("").mkdirs();
		}
		
	}
	
	public static void main(String[] args) {
		setUpWikibot();
		checkDirectories();
		createGui();
	}
}
