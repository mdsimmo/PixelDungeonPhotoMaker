Pixel Dungeon Photo Maker
======================

This project is to create images for the <a href="http://pixeldungeon.wikia.com">Pixel Dungeon Wiki</a>.
It currently can create the infobox images, glowing enchantments, particle effects and enemy animations.

## Usage

The app contains a simple command line passer which makes small jobs easy.
Alternatively, you can use the project as a library and call the code.

The examples below give the standard values used on the Wiki

When using the command line passer, make sure to wrap everything in double quotes (`"`) and use single quotes (`'`) for paths. For example:

<pre>
java -jar PixelDungeonPhotoMaker.jar "{ load 'items.png' | crop x:0 y:0 w:16 h:16 | scale x:3 y:3 | save 'output.png' }"
</pre>

### Simple Image

![Simple Image Example](https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/src/main/resources/example_simple.png)

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

<img src="https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/src/main/resources/example_infobox.png" width="100" alt="Infobox Example" />

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

![Animated Enemy Example](https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/src/main/resources/example_enemy.gif)

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

![Glowing Item Example](https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/src/main/resources/example_glow.gif)

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

![Particles Example](https://github.com/mdsimmo/PixelDungeonPhotoMaker/raw/master/src/main/resources/example_particles.gif)

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