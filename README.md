Pixel Dungeon Photo Maker
======================

This project is to create images for the <a href="http://pixeldungeon.wikia.com">Pixel Dungeon Wiki</a>.
It currently can create the infobox images, glowing enchantments, particle effects and enemy animations.

## Usage

The app contains a simple command line passer which makes small jobs easy.
Alternatively, you can use the project as a library and call the code.

When using the command line passer, make sure to wrap everything in double quotes (`"`) and use single quotes (`'`) for paths. For example:

<pre>
java -jar PixelDungeonPhotoMaker.jar "{ load 'items.png' | crop x:0 y:0 w:16 h:16 | scale x:3 y:3 | save 'output.png' }"
</pre>

Template images can be found here: [PixelDungeonPhotoMaker/images](https://github.com/mdsimmo/PixelDungeonPhotoMaker/tree/master/images)

Game assets can be found here: [PixelDungeon/assets](https://github.com/watabou/pixel-dungeon/tree/master/assets)

The examples below give the standard values used on the Wiki

### Simple Image

![Simple Image Example](https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/images/example_simple.png)

    { load 'items.png' | crop x:0 y:0 w:16 h:16 | scale x:3 y:3 | save 'output.png' }

```kotlin
val itemSheet: BufferedImage = ... // load items.png
PipeLine(
        Crop(Rectangle(0, 0, 16, 16)),
        Scale(3.0, 3.0),
        Save(File("output.png"))
).process(itemSheet)
```

### Infobox

<img src="https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/images/example_infobox.png" width="100" alt="Infobox Example" />

Infoboxes are the same for both enemies and items
   
    { load 'items.png' | crop x:0 y:0 w:16 h:16 | scale x:14 y:14 | trim | shadow r:12 a:0.8 x:0 y:0 | backimage {load 'infoboxcity.png'} | save 'output.png' }
    
```kotlin
val itemSheet: BufferedImage = ... // load items.png
val background: BufferedImage = ... // load infoboxcity.png
PipeLine(
        Crop(Rectangle(0, 0, 16, 16)),
        Scale(14.0, 14.0),
        Trim(),
        Shadow(12, 0.8f, 0, 0),
        BackImage(background),
        Save(File("output.png"))
).process(itemSheet)
```    

### Animated Enemy

![Animated Enemy Example](https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/images/example_enemy.gif)

Note: There is currently no support for frames of different length 

    { load 'bat.png' | spritesheet w:15 h:16 frames:1,2,3,2,1 | scale x:3 y:3 | save 'bat.gif' msbtwframes:100 }

```kotlin
val itemSheet: BufferedImage = ... // load bat.png
PipeLine(
        SpriteSheet(15, 16, listOf(1, 2, 3, 2, 1)),
        Scale(3.0, 3.0),
        Save(File("bat.gif"), msBtwFrames = 100)
).process(itemSheet)
```    

### Glowing Item

![Glowing Item Example](https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/images/example_glow.gif)

    { load 'items.png' | spritesheet w:16 h:16 frames:2 | scale x:3 y:3 | glow r:255 g:10 b:20 length:30 | filltransparent r:71 g:70 b:70 | save 'glow.gif' msbtwframes:40 }
    
```kotlin
val itemSheet: BufferedImage = ... // load items.png
PipeLine(
        SpriteSheet(16, 16, listOf(2)),
        Scale(3.0, 3.0),
        Glow(Color(255, 10, 20), 30),
        FillTransparent(Color(71, 70, 70)),
        Save(File("glow.gif"), msBtwFrames = 40)
).process(itemSheet)
```

### Particles

![Particles Example](https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/images/example_particles.gif)

        { load 'well.png' | scale x:3 y:3 | particles type:popup framesperspawn:10 length:100 scale:3 texture:{ load 'specks.png' | spritesheet w:7 h:7 frames:3 } | filltransparent r:71 g:70 b:70 | save 'part.gif' msbtwframes:40 } 

```kotlin
val well: BufferedImage = ... // load well.png
val specksSheet: BufferedImage = ... // load specks.png

val speck = PipeLine(SpriteSheet(7, 7, listOf(4)))
    .process(specksSheet)

PipeLine(
        Scale(3.0, 3.0),
        Particles(Emitter(PopUp(), framesPerSpawn = 10, loopTime = 100), specks, 3.0f, 100),
        FillTransparent(Color(71, 70, 70)),
        Save(File("well.gif"), msBtwFrames = 40)
).process(well)
```

### All Processes

Warning: Do not use two 'animated' commands together 

| Name  |   Arg   | Default | Description
| ------|---------|---------|------------
| backfill |      |         | Puts a solid background color
|       | r       |         | Color red
|       | g       |         | Color green
|       | b       |         | Color blue
| backimage |     |         | Puts an image behind. Images are centered
|       | _arg 1_ |         | The image to use
| crop  |         |         | crops a part of the image out
|       | x       |         | left most pixel
|       | y       |         | top most pixel
|       | w       |         | crop width
|       | h       |         | crop height
| filltransparent ||        | Same as backfill but only applies to semi-transparent pixels 
|       | r       |         | The backing color red
|       | g       |         | The backing color green
|       | b       |         | The backing color blue
| glow  |         |         | Animates with a fading glow cycle
|       | r       |         | Glow red
|       | g       |         | Glow green
|       | b       |         | Glow blue
|       | length  |         | Number of frames for a complete glow cycle
| load  |         |         | Loads an image from disk
|       | _arg 1_ |         | File path to an image to load
| particles |     |         | Animates with a particle effect
|       | type    |         | The particle type (`bubble`, `floatup`, `flyaway`, `popup`)
|       | framesperspawn |  | How many frames between each particle spawning
|       | length  |         | How many frames before the particles repeat
|       | texture |         | the partcle image
|       | scale   |         | how much to scale the particle effect by
| save  |         |         | Writes the image to disk
|       | _arg 1_ |         | File path to write to
|       | msbtwframes |  0  | Gifs only: number of milliseconds between frames
|       | looped  | true    | Gifs only: should the animation repeat (default: true)
| scale |         |         | Scales the image by an amount
|       | x       |         | x scale
|       | y       |         | y scale
| shadow |        |         | Applies a drop shadow
|       | r       |         | Shadow fade radius (px)
|       | a       |         | Shadow alpha
|       | x       |         | Shadow x offset
|       | y       |         | Shadow y offset
| spritesheet |   |         | Crops an image/animation from a spritesheet
|       | w       |         | The width of the crop
|       | h       |         | The height of the crop
|       | xoffset | 0       | Where to start on the image (px)
|       | yoffset | 0       | Where to start on the image (px)
|       | xspace  | 0       | Spacing between frames (px)
|       | yspace  | 0       | Spacing between frames (px)
|       | frames  |         | frame numbers to choose (comma seperated)
| trim  |         |         | Removes all whitespace around the image
