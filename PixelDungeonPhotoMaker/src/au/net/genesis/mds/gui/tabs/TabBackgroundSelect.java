package au.net.genesis.mds.gui.tabs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.gui.MainGui;

public class TabBackgroundSelect extends JPanel implements ActionListener {

	public interface BackgroundListener {
		public void backgroundChange(InfoboxBack back);
	}
	
	private static final long serialVersionUID = 1L;

	private ArrayList<BackgroundListener> listeners = new ArrayList<TabBackgroundSelect.BackgroundListener>();
	
	private JButton sewers = MainGui.createButton("Sewers", InfoboxBack.SEWER.getImagePath());
	private JButton prison = MainGui.createButton("Prison", InfoboxBack.PRISON.getImagePath());
	private JButton caves = MainGui.createButton("Caves", InfoboxBack.CAVES.getImagePath());
	private JButton city = MainGui.createButton("Dwarf City", InfoboxBack.CITY.getImagePath());
	private JButton halls = MainGui.createButton("Demon Halls", InfoboxBack.HALLS.getImagePath());
	
	public TabBackgroundSelect() {
		this.setLayout(new FlowLayout());
		sewers.addActionListener(this);
		prison.addActionListener(this);
		caves.addActionListener(this);
		city.addActionListener(this);
		halls.addActionListener(this);
		add(sewers);
		add(prison);
		add(caves);
		add(city);
		add(halls);
	}
	
	public void addListener(BackgroundListener l) {
		this.listeners.add(l);
	}

	private void updateListeners(InfoboxBack background) {
		for (BackgroundListener l : listeners) {
			l.backgroundChange(background);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sewers)
			updateListeners(InfoboxBack.SEWER);
		if (e.getSource() == prison)
			updateListeners(InfoboxBack.PRISON);
		if (e.getSource() == caves)
			updateListeners(InfoboxBack.CAVES);
		if (e.getSource() == city)
			updateListeners(InfoboxBack.CITY);
		if (e.getSource() == halls)
			updateListeners(InfoboxBack.HALLS);
	}
}