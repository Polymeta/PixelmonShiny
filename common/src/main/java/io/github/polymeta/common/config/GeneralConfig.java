package io.github.polymeta.common.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.Arrays;
import java.util.List;

@ConfigSerializable
public class GeneralConfig
{
    @Setting(comment = "The Title of the GUI window, can use mc colours as well")
    public String guiTitle = "Shiny Modificator";
    @Setting(comment = "The ItemType for shinyItem. Can be modded items via modid:itemid. Will default to Sponge if value cant be read")
    public String itemType = "minecraft:sponge";
    @Setting(comment = "The Display name, supports colours and text styles")
    public String itemName = "ShinyItem";
    @Setting(comment = "ItemLore, this (together with name) will be used to check if a shinyItem is legitimate")
    public List<String> itemLore = Arrays.asList("Turns a normal pokemon into a shiny!", "Right click to use me!");
}
