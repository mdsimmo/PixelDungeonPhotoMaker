package au.net.genesis.mds.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LaunchingGui implements Gui, ActionListener {
	
	private MainGui mainGui;
	
	public LaunchingGui(MainGui mainGui) {
		this.mainGui = mainGui;
	}

	@Override
	public Gui setUp(JFrame f) {
		Container c = f.getContentPane();
		JButton enemyButton = MainGui.creatButton("Enemy Infobox", "exampleenemy.png");
		enemyButton.addActionListener(this);
		enemyButton.setActionCommand("enemyButton");
		c.add(enemyButton);
		JButton itemButton = MainGui.creatButton("Item Infobox", "exampleenemy.png");
		c.add(itemButton);
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "enemyButton") {
			mainGui.setContent(new InfoboxGui());
		}
	}

}
