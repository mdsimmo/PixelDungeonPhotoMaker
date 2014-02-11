Pixel Dungeon Photo Maker
======================

<h3>Description</h3>
This project is to create images for the <a href="http://pixeldungeon.wikia.com">Pixel Dungeon Wiki</a>. It currently can only create the infobox images.

<h3>How it works</h3>
A simple program would look something like this:
    
    ItemCreator ic = new ItemCreator();
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
