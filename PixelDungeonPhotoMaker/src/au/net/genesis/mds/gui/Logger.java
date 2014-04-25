package au.net.genesis.mds.gui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Logger extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTextArea text = new JTextArea();
	private JScrollPane scrollPane;
	
	public Logger() {
		text.setEditable(false);
		scrollPane = new JScrollPane(text);
		scrollPane.setPreferredSize(new Dimension(256,128));
		this.add(scrollPane);
	}
	
	public void log(String string) {
		// TODO this logger prints at strange times (threading issues?)
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				text.append("   " + "Hello" + "\n");
				scrollPane.getViewport().setViewPosition(new Point(0,text.getHeight()));
				revalidate();
				repaint();
				
			}
		});
	}
	
}
