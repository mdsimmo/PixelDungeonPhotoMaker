Pixel Dungeon Photo Maker
======================

<h3>Description</h3>
This project is to create images for the <a href="http://pixeldungeon.wikia.com">Pixel Dungeon Wiki</a>. It currently can only create the infobox images and basic images.

<h3>How to use it</h3>

There is a gui available which makes things easy, or you can use code:

A simple program would look something like this:
    
    InfoboxCreator ic = new InfoboxCreator();
    ic.setAsset("assets/items.png")
			.setBackground(InfoboxBack.SEWER)
			.configureShadow(16, 1.0F)
			.setSelection(new Rectangle(4*16,3*16,16,16))
			.setItemScale(12);
	
	// save the image
	try {
		ImageIO.write(ic.getImage(), "png", new File("saved.png"));
	} catch (IOException e) {
		e.printStackTrace();
	}

The code version also features a article system not available in the gui version (but it will be coming soon (hopefuly))
