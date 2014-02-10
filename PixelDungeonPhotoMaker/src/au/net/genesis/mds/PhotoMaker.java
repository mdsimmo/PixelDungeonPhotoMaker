package au.net.genesis.mds;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class PhotoMaker extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton openButton;	
	
	public PhotoMaker() {
		openButton = new JButton();
		Container c = this.getContentPane();
		c.add(openButton);
		openButton.addActionListener(this);
		
	}
	
	public void click() {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(this);
		System.out.print(fc.getSelectedFile());
	}

	public static void main(String[] args) {
		PhotoMaker photoMaker = new PhotoMaker();
		photoMaker.setSize(new Dimension(640, 480));
		photoMaker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		photoMaker.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.print("Hi");
		if (e.getSource() == openButton) {
			click();
		}
		
	}
}
