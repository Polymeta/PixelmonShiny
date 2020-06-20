package io.github.polymeta.common.adapter;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

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
     * @param clickedItem The itemstack that got clicked to determine which pokemon gets changed
     */
    void toggleShinyInSlot(@NonNull Player player, ItemStackSnapshot clickedItem);
}
