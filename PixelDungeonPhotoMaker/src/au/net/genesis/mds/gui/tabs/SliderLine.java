package au.net.genesis.mds.gui.tabs;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderLine extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	private JLabel label, value;
	private JSlider slider;
	private TYPE display;
	
	public enum TYPE {
		NONE,
		PIXELS,
		PERCENT;
	}
	
	public SliderLine(String label) {
		this(label, TYPE.NONE);
	}
	
	public SliderLine(String label, TYPE display) {
		this.label = new JLabel();
		this.label.setText(label);
		this.display = display;
		slider = new JSlider();
		slider.addChangeListener(this);
		value = new JLabel();
		
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(Box.createHorizontalStrut(16));
		add(this.label);
		add(Box.createHorizontalStrut(16));
		add(slider);
		add(Box.createHorizontalStrut(16));
		add(value);
		add(Box.createHorizontalStrut(16));
	}
	
	public JSlider getSlider() {
		return slider;
	}
	
	public SliderLine initTo(int min, int max, int start) {
		slider.setMaximum(min);
		slider.setMaximum(max);
		slider.setValue(start);
		return this;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == slider) {
			String text = Integer.toString(slider.getValue());
			switch (display) {
				case PIXELS:
					value.setText(text + " px");
					break;
				case PERCENT:
					value.setText(text + " %");
					break;
				case NONE:
				default:
					value.setText(text);
					break;
			}
		}
	}	
}
