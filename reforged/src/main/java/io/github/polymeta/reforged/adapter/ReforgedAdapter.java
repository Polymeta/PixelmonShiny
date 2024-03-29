package io.github.polymeta.reforged.adapter;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import io.github.polymeta.common.adapter.IPixelmonAdapter;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReforgedAdapter implements IPixelmonAdapter
{
    @Override
    public List<ItemStack> getPartyAsItem(Player player)
    {
        PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player.getUniqueId());
        return Arrays.stream(storage.getAll())
                .map(pokemon -> {
                    if(pokemon == null)
                        return ItemStack.empty();

                    net.minecraft.item.ItemStack photo = ItemPixelmonSprite.getPhoto(pokemon);
                    ItemStack item = (ItemStack)(Object)photo;
                    item.offer(Keys.DISPLAY_NAME, Text.of(pokemon.getSpecies().name));
                    if(pokemon.isShiny())
                    {
                        item.offer(Keys.ITEM_LORE, Arrays.asList(Text.EMPTY, Text.of("Click me to turn me into non-shiny!")));
                    }
                    else{
                        item.offer(Keys.ITEM_LORE, Arrays.asList(Text.EMPTY, Text.of("Click me to turn me into shiny!")));
                    }
                    return item;
                }).collect(Collectors.toList());
    }

    @Override
    public void toggleShinyInSlot(Player player, int partySlot)
    {
        PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player.getUniqueId());
        storage.getAll()[partySlot].setShiny(!storage.getAll()[partySlot].isShiny());
    }
}
