package io.github.polymeta.generations.adapter;

import com.pixelmongenerations.common.entity.pixelmon.stats.links.NBTLink;
import com.pixelmongenerations.common.item.ItemPixelmonSprite;
import com.pixelmongenerations.core.storage.NbtKeys;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import io.github.polymeta.common.adapter.IPixelmonAdapter;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenerationsAdapter implements IPixelmonAdapter
{
    @Override
    public List<ItemStack> getPartyAsItem(Player player)
    {
        PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorageFromUUID(player.getUniqueId()).orElseThrow(NullPointerException::new);
        return Arrays.stream(storage.partyPokemon)
                .map(nbt -> {
                    if(nbt == null)
                        return ItemStack.empty();

                    NBTLink nbtLink = new NBTLink(nbt);
                    net.minecraft.item.ItemStack photo = ItemPixelmonSprite.getPhoto(nbtLink);
                    ItemStack item = (ItemStack)(Object)photo;
                    item.offer(Keys.DISPLAY_NAME, Text.of(nbtLink.getSpecies().name));
                    if(nbtLink.isShiny())
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
        PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorageFromUUID(player.getUniqueId()).orElseThrow(NullPointerException::new);
        storage.partyPokemon[partySlot].setBoolean(NbtKeys.IS_SHINY, !storage.partyPokemon[partySlot].getBoolean(NbtKeys.IS_SHINY));
    }
}
