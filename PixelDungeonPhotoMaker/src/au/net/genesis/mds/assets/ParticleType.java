package au.net.genesis.mds.assets;

public enum ParticleType {

	PLUS("plus.png"), MARK("mark.png"), BUBBLE("bubble.png"), BUTTERFLY(
			"butterfly.png");

	private String file;

	ParticleType(String file) {
		this.file = file;
	}

	public String getFile() {
		return "resources/" + file;
	}

}