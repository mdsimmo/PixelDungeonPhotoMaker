package au.net.genesis.mds.gui.tabs;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class InputLine extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JTextComponent text;
	
	public enum Type {
		STANDARD,
		PASSWORD;
	}
	
	public InputLine (String label) {
		this(label, Type.STANDARD);
	}
	
	public InputLine(String label, Type type) {
		this.label = new JLabel();
		this.label.setText(label);
		switch (type) {
			case PASSWORD:
				text = new JPasswordField();
				break;
			case STANDARD:
			default:
				text = new JTextField();
				break;
		}
		// what max height is seems to have no effect
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(Box.createHorizontalStrut(16));
		add(this.label);
		add(Box.createHorizontalStrut(16));
		add(text);
		add(Box.createHorizontalStrut(16));
	}
	
	public String getText() {
		return text.getText();
	}
}
