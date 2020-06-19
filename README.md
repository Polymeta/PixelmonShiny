# PixelmonShiny
Welcome to PixelmonShiny! This plugin is my first attempt to make a project serving both PixelmonGenerations and Reforged with as much common code in between as possible.

### What does it do?
PixelmonShiny allows you to give a (configurable) item to players, which can then be used to turn one pokemon in their party into it's shiny or non-shiny form.
The selection is made over a GUI and the players can simply click the pokemon they want to modify.

### Command/Permission
This plugin consists of only one command which is as follows:

* `/giveshinyitem <player> [amount]` **Permission:** `pixelmonshiny.give`.
Gives a ShinyItem based on the configuration to the specified player (or yourself if you leave it out). Amount is optional and defaults to 1.

When you obtain a ShinyItem, all you have to do is right click while you are holding it in your main hand and it will bring up the PartyGUI for you to select from!

### Configuration
Upon first starting your server with this plugin, it will automatically generate a default configuration for you already, so you could use it straight out of the box! 
The default configuration will also contain plenty of comments to guide you through it, but just for coverage, I will list them here as well:

* guiTitle: This represents the title in the inventory window that gets openend when the player right clicks the ShinyItem. It supports minecraft colour codes (like &c for example)

* itemType: What kind of item you want the ShinyItem to be. This gets done over the \<modid\>:\<itemid\> syntax and can therefore also use pixelmon items! **WARNING:** Changing this will result in ShinyItems, which have been given out prior to changing this, breaking and no longer functioning as ShinyItems!

* itemName: The display name of the ShinyItem in game. Also supports minecraft colour codes. **WARNING:** Changing this will result in ShinyItems, which have been given out prior to changing this, breaking and no longer functioning as ShinyItems!

* itemLore: This represents the purple lore text that any item can have. This takes a list of texts which represent one lore line each. This also supports minecraft colour codes.  **WARNING:** Changing this will result in ShinyItems, which have been given out prior to changing this, breaking and no longer functioning as ShinyItems!

That's about it for this plugin already, it's a small project looking at it, but I tried to learn more about the building process, including signing and maybe even automatic deployment (but this is far far away for now).


### TO-DO(?)
Currently, I can't think of addtional things to add, but if you feel like something is wrong or missing, please open an issue and I'll see what I can do!

Thank you for looking at my plugin and reading until the end of this readme. :)
