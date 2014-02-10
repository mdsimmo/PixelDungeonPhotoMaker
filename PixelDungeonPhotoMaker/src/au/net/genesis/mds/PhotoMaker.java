package au.net.genesis.mds;

import java.awt.Dimension;

import javax.swing.JFrame;

public class PhotoMaker extends JFrame {

	private static final long serialVersionUID = 1L;

	public PhotoMaker() {

	}

	public static void main(String[] args) {
		PhotoMaker photoMaker = new PhotoMaker();
		photoMaker.setPreferredSize(new Dimension(640, 480));
		photoMaker.setVisible(true);
	}
}
