package au.net.genesis.mds;

import javax.swing.JFrame;

import org.wikipedia.Wiki;

import au.net.genesis.mds.gui.MainGui;

public class PhotoMaker {
	
	public static Wiki wiki;

	/*
	 * TODO list: 
	 * create own image drop shadow [optional] 
	 * make an auto gif
	 * creator make enemy gifs 
	 * make standard images
	 * gui version?
	 */

	public static void main(String[] args) {
		wiki = new Wiki("pixeldungeon.wikia.com", "");
		wiki.setUsingCompressedRequests(false);
		
		MainGui mainGui = new MainGui();
		mainGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGui.setVisible(true);
		
		
	}

	/**
	 * Gets the path to the resource folder
	 * 
	 * @param string
	 *            the location inside the resource directory
	 * @return the resource file's path
	 */
	public static String getResource(String string) {
		return "resources/" + string;

	}

}
