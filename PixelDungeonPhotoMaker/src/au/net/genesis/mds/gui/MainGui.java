package au.net.genesis.mds.gui;

import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MainGui extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private PreviewPanel previewPanel = new PreviewPanel();
	private JPanel container = new JPanel();
	private OptionsPanel optionPanel = new OptionsPanel(previewPanel);
	
	public MainGui() {
		getContentPane().add(container);
		container.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		container.add(optionPanel);
		container.add(new JSeparator(SwingConstants.VERTICAL));
		container.add(previewPanel);
		container.add(Box.createHorizontalGlue());
		this.pack();
	}

	public void updatePreview(BufferedImage image) {
		previewPanel.updateImage(image);
	}
	
	public PreviewPanel getPreviewPanel() {
		return previewPanel;
	}

}
