package io.github.polymeta.reforged.adapter;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import io.github.polymeta.common.adapter.IPixelmonAdapter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReforgedAdapter implements IPixelmonAdapter
{
    @Override
    public List<ItemStack> getPartyAsItem(@NonNull Player player)
    {
        PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player.getUniqueId());
        return storage.getTeam().stream()
                .map(pokemon -> {
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
    public void toggleShinyInSlot(@NonNull Player player, ItemStackSnapshot clicked)
    {
        PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player.getUniqueId());
        storage.getTeam().stream()
                .filter(pokemon -> Text.of(pokemon.getSpecies().name).equals(clicked.get(Keys.DISPLAY_NAME).orElse(Text.EMPTY)))
                .findFirst()
                .ifPresent(pokemon -> pokemon.setShiny(!pokemon.isShiny()));
    }
}
