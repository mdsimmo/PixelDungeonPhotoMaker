package au.net.genesis.mds.gui.old;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderGui extends JPanel implements ChangeListener {
	
	private static final long serialVersionUID = 1L;
	private JSlider slider;
	private JLabel label;
	private JLabel value;
	private int min = 1, max = 20, start = 12;
	
	public SliderGui(String text) {
		label = new JLabel(text);
		this.add(label);
		slider = new JSlider(min, max, start);
		this.add(slider);
		slider.addChangeListener(this);
		value = new JLabel(Integer.toString(slider.getValue()));
		this.add(value);
	}
	
	public SliderGui setRange(int min, int max, int current) {
		this.min = min;
		this.max = max;
		this.start = current;
		if (slider != null) {
			slider.setMinimum(min);
			slider.setMaximum(max);
			slider.setValue(current);
		}
		return this;
	}
	public JLabel getLabel() {
		return label;
	}
	public JSlider getSlider() {
		return slider;
	}
	public JLabel getValueLabel() {
		return value;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JSlider) {
			value.setText(Integer.toString(((JSlider)e.getSource()).getValue()));
		}
		
	}
}
