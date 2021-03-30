package io.github.polymeta.common.config;

import com.google.common.reflect.TypeToken;
import io.github.polymeta.common.utility.Utils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;

public class GeneralConfigManager
{
    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationNode node;
    private GeneralConfig config;

    public GeneralConfigManager(ConfigurationLoader<CommentedConfigurationNode> _loader)
    {
        this.loader = _loader;
    }

    public GeneralConfig getConfig()
    {
        if(config == null)
        {
            if(this.LoadConfig())
            {
                return this.config;
            }
            else {
                System.out.print("Warning! Failed to get Config");
                return null;
            }
        }
        else return this.config;
    }

    @SuppressWarnings("UnstableApiUsage")
    public boolean LoadConfig()
    {
        try {
            this.node = loader.load();
            TypeToken<GeneralConfig> type = TypeToken.of(GeneralConfig.class);
            this.config = node.getValue(type, new GeneralConfig());
            node.setValue(type, this.config);
            this.loader.save(node);
            return true;
        } catch (IOException | ObjectMappingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings({"unused", "UnstableApiUsage"})
    public void saveConfig() {
        try {
            TypeToken<GeneralConfig> type = TypeToken.of(GeneralConfig.class);
            node.setValue(type, this.config);
            this.loader.save(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemStack getShinyItem()
    {
        return ItemStack.builder()
                .itemType(Sponge.getRegistry().getType(ItemType.class, this.config.itemType).orElse(ItemTypes.SPONGE))
                .add(Keys.DISPLAY_NAME, TextSerializers.FORMATTING_CODE.deserialize(this.config.itemName))
                .add(Keys.ITEM_LORE, Utils.deserializeLore(this.config.itemLore))
                .build();
    }
}
