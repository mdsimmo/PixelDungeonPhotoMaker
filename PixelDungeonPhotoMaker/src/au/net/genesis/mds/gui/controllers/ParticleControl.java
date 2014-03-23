package au.net.genesis.mds.gui.controllers;

import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.assets.WellType;
import au.net.genesis.mds.gui.MainGui;
import au.net.genesis.mds.gui.OptionsPanel;
import au.net.genesis.mds.gui.tabs.TabAssetSelector;
import au.net.genesis.mds.gui.tabs.TabAssetSelector.SelectorListener;
import au.net.genesis.mds.imageEditors.ParticleScene;
import au.net.genesis.mds.particles.Particle.ParticleSystem;

public class ParticleControl extends TabControl implements SelectorListener, ChangeListener {
	
	private ParticleScene ps;
	private TabAssetSelector assetSelector;
	private Rectangle particleSelection = new Rectangle(8,8);
	private File particleFile = AssetFinder.getDungeonFile("specks.png");
	private BufferedImage particleImage;
	private JSlider particleScale, imageWidth, imageHeight;
	private JLabel scaleLabel, widthLabel, heightLabel;
	private JButton buttonAlchemy = MainGui.createButton("Alchemy pot", WellType.ALCHEMY.getFile());
	private JButton buttonWaterWell = MainGui.createButton("Water well", WellType.MAGIC_WELL.getFile());
	private JButton popSystem, flySystem, bubbleSystem, floatSystem;
	private File outputFile = AssetFinder.getTempFile("");
		
	public ParticleControl(OptionsPanel optionsPanel) {
		super(optionsPanel);
		ps = new ParticleScene()
			.setOutputFile(outputFile);
		
		imageWidth = new JSlider(16, 256, 96);
		imageWidth.addChangeListener(this);
		widthLabel = new JLabel(Integer.toString(imageWidth.getValue()) + " px");
		
		imageHeight = new JSlider(16, 256, 96);
		imageHeight.addChangeListener(this);
		heightLabel = new JLabel(Integer.toString(imageHeight.getValue()) + " px");	
		
		particleScale = new JSlider(5,200,60);
		particleScale.addChangeListener(this);
		scaleLabel = new JLabel(Integer.toString(particleScale.getValue()) + "%");
		
		assetSelector = new TabAssetSelector();
		assetSelector.addSelectorListener(this);
		assetSelector.setAssetFile(particleFile);
		assetSelector.setSelection(particleSelection);
		
		buttonAlchemy.addActionListener(this);
		buttonWaterWell.addActionListener(this);
		
		popSystem = new JButton("Pop up");
		popSystem.addActionListener(this);
		flySystem = new JButton("Fly away");
		flySystem.addActionListener(this);
		bubbleSystem = new JButton("Bubble");
		bubbleSystem.addActionListener(this);
		floatSystem = new JButton("Float up");;
		floatSystem.addActionListener(this);
	}

	@Override
	public void configureAssetTab(JPanel panel) {
		panel.add(assetSelector);
	}

	@Override
	public void configureOptionTab(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel scale = new JPanel();
		scale.setLayout(new BoxLayout(scale, BoxLayout.X_AXIS));
		scale.add(new JLabel("Particle scale"));
		scale.add(particleScale);
		scale.add(scaleLabel);
		panel.add(scale);
		
		JPanel width = new JPanel();
		width.setLayout(new BoxLayout(width, BoxLayout.X_AXIS));
		width.add(new JLabel("Well width"));
		width.add(imageWidth);
		width.add(widthLabel);
		panel.add(width);
		
		JPanel height = new JPanel();
		height.setLayout(new BoxLayout(height, BoxLayout.X_AXIS));
		height.add(new JLabel("Well height"));
		height.add(imageHeight);
		height.add(heightLabel);
		panel.add(height);
		
		JPanel wellType = new JPanel();
		wellType.setLayout(new BoxLayout(wellType, BoxLayout.X_AXIS));
		wellType.add(new JLabel("Well type:"));
		wellType.add(buttonAlchemy);
		wellType.add(buttonWaterWell);
		panel.add(wellType);
		
		
		
		JPanel systemType = new JPanel();
		systemType.setLayout(new FlowLayout());
		systemType.add(new JLabel("Particle system type:"));
		systemType.add(popSystem);
		systemType.add(floatSystem);
		systemType.add(bubbleSystem);
		systemType.add(flySystem);
		panel.add(systemType);
	}

	@Override
	public File createImage() {
		outputFile = ps.createScene();
		return outputFile;
	}
	
	@Override
	public String getName() {
		return "Particle Scene";
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
		if (e.getSource() == buttonAlchemy)
			ps.setWellType(WellType.ALCHEMY);

		if (e.getSource() == buttonWaterWell)
			ps.setWellType(WellType.MAGIC_WELL);
		
		if (e.getSource() == floatSystem)
			ps.getEmitter().setParticleSystem(ParticleSystem.FLOAT_UP);

		if (e.getSource() == bubbleSystem)
			ps.getEmitter().setParticleSystem(ParticleSystem.BUBBLE);
		
		if (e.getSource() == flySystem)
			ps.getEmitter().setParticleSystem(ParticleSystem.FLY_AWAY);
		
		if (e.getSource() == popSystem)
			ps.getEmitter().setParticleSystem(ParticleSystem.POP_UP);
			
		refreshPreview();
	}

	@Override
	public void selectionChange(Rectangle selection) {
		this.particleSelection = selection;
		getParticleImage();
		refreshPreview();
	}
	
	@Override
	public JButton getMenuButton() {
		if (menuButon == null) {
			menuButon = MainGui.createButton("Particle scene",AssetFinder.getImageFile("exampleparticle.gif"));
			menuButon.addActionListener(this);
		}
		return menuButon;
	}
	
	@Override
	public void assetChange(File file) {
		this.particleFile = file;
		getParticleImage();
		refreshPreview();
	}
	
	private void getParticleImage() {
		BufferedImage temp;
		ps.getEmitter().setParticleScale((float)particleScale.getValue()/100F);
		particleImage = new BufferedImage(particleSelection.width,
				particleSelection.height, BufferedImage.TYPE_INT_ARGB);
		temp = AssetFinder.loadImage(particleFile);
		particleImage.getGraphics().drawImage(temp, -particleSelection.x, -particleSelection.y, null);
		
		// remove whitespace (lets the image can be auto centered)
		int minx = particleImage.getWidth() / 2;
		int maxx = minx;
		int miny = particleImage.getHeight() / 2;
		int maxy = miny;
		for (int i = 0; i < particleImage.getWidth(); i++) {
			for (int j = 0; j < particleImage.getHeight(); j++) {
				boolean isOpaque = ((particleImage.getRGB(i, j) & 0xFF000000) != 0x00000000);
				if (isOpaque) {
					if (i < minx)
						minx = i;
					if (i > maxx)
						maxx = i;
					if (j < miny)
						miny = j;
					if (j > maxy)
						maxy = j;
				}
			}
		}
		temp = new BufferedImage(maxx - minx + 1, maxy - miny
				+ 1, BufferedImage.TYPE_INT_ARGB);
		temp.getGraphics().drawImage(particleImage, -minx, -miny, null);
		particleImage = temp;
		temp.flush();
		ps.getEmitter().setParticleImg(particleImage);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == particleScale) {
			scaleLabel.setText(Integer.toString(particleScale.getValue()) + "%");
			getParticleImage();
			refreshPreview();
			return;
		}
		
		if (e.getSource() == imageWidth || e.getSource() == imageHeight) {
			widthLabel.setText(Integer.toString(imageWidth.getValue()) + " px");
			heightLabel.setText(Integer.toString(imageHeight.getValue()) + " px");
			ps.setSceneSize(imageWidth.getValue(), imageHeight.getValue());
			refreshPreview();
			return;
		}
	}

}
