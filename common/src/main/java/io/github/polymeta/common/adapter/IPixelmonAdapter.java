package io.github.polymeta.common.adapter;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;

public interface IPixelmonAdapter
{
    /**
     * @param player Player whose party we are looking at
     * @return Player's party in itemstack format with sprites and lore
     */
    List<ItemStack> getPartyAsItem(@NonNull Player player);

    /**
     * @param player The player whose party we are looking at
     * @param i slot in party to be toggles, 0-5
     */
    void toggleShinyInSlot(@NonNull Player player, int i);
}
