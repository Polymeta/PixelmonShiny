package io.github.polymeta.common.listener;

import io.github.polymeta.common.GUI.PartyGUI;
import io.github.polymeta.common.config.GeneralConfigManager;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.List;

public class SpongeListener
{
    private GeneralConfigManager cManager;

    public SpongeListener(GeneralConfigManager _configManager)
    {
        this.cManager = _configManager;
    }

    @Listener
    public void onRightClick(InteractBlockEvent.Secondary event, @Root Player player)
    {
        player.getItemInHand(HandTypes.MAIN_HAND).ifPresent(itemStack ->
        {
            if(itemStack.getType().equals(cManager.getShinyItem().getType()))
            {
                itemStack.get(Keys.DISPLAY_NAME).ifPresent(text -> {
                    if(TextSerializers.FORMATTING_CODE.serialize(text).equalsIgnoreCase(cManager.getConfig().itemName))
                    {
                        itemStack.get(Keys.ITEM_LORE).ifPresent(lore -> {
                            if(this.isLoreSame(lore, cManager.getConfig().itemLore))
                            {
                                //type, name and lore match, so now we open GUI!
                                PartyGUI.OpenInventoryOnPlayer(player);
                                event.setCancelled(true);
                            }
                        });
                    }
                });
            }
        });
    }

    private boolean isLoreSame(List<Text> lore1, List<String> lore2)
    {
        if (lore1.size() == lore2.size())
        {
            for (int i = 0; i < lore1.size(); i++)
            {
                if (!TextSerializers.FORMATTING_CODE.serialize(lore1.get(i)).equalsIgnoreCase(lore2.get(i)))
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
        return true;
    }
}
