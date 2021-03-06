package io.github.polymeta.common.GUI;

import io.github.polymeta.common.adapter.IPixelmonAdapter;
import io.github.polymeta.common.config.GeneralConfigManager;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.List;

public class PartyGUI
{
    private static boolean isReady = false;

    private static Inventory inventory;
    private static ItemStack border = ItemStack.builder()
            .itemType(ItemTypes.STAINED_GLASS_PANE)
            .add(Keys.DYE_COLOR, DyeColors.RED)
            .add(Keys.DISPLAY_NAME, Text.EMPTY)
            .build();

    private static IPixelmonAdapter adapter;
    private static Object plugin;

    public static void initGUI(@NonNull Object _plugin, @NonNull GeneralConfigManager _cMan, @NonNull IPixelmonAdapter _adapter)
    {
        plugin = _plugin;
        adapter = _adapter;
        inventory = Inventory.builder()
                .of(InventoryArchetypes.CHEST)
                .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(TextSerializers.FORMATTING_CODE.deserialize(_cMan.getConfig().guiTitle)))
                .listener(ClickInventoryEvent.class, e -> e.setCancelled(true))
                .listener(ClickInventoryEvent.Primary.class, PartyGUI::fireClickEvent)
                .build(plugin);
        placeBorder();
        isReady = true;
    }

    private static void fireClickEvent(ClickInventoryEvent.Primary event)
    {
        event.setCancelled(true);
        if(event.getCause().root() instanceof Player)
        {
            Player player = (Player) event.getCause().root();

            ItemStackSnapshot clicked = event.getCursorTransaction().getFinal();
            //simple check -> we do nothing when we click border
            if(clicked.getType().equals(border.getType()))
                return;
            if(clicked.isEmpty())
                return;

            //prevents sponge warning when closing inventory without delay
            Sponge.getScheduler().createTaskBuilder().delayTicks(3L).execute(() -> {
                //feels hacky, but should work
                adapter.toggleShinyInSlot(player, clicked);
                //we know they have an item as that's how the inventory gets opened
                player.getItemInHand(HandTypes.MAIN_HAND).get().setQuantity(player.getItemInHand(HandTypes.MAIN_HAND).get().getQuantity() - 1);
                player.closeInventory();
            }).submit(plugin);
        }
    }

    public static void OpenInventoryOnPlayer(@NonNull Player _player)
    {
        if(!isReady)
            return;

        List<ItemStack> pokeParty = adapter.getPartyAsItem(_player);
        for(int i = 0; i < pokeParty.size(); i++)
        {
            if(pokeParty.get(i) == null)
                continue;

            inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(i >= 4 ? i + 2 : i + 1, 1))).set(pokeParty.get(i));
        }
        _player.openInventory(inventory);
    }

    private static void placeBorder()
    {
        //top row
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(0, 0))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(1, 0))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(2, 0))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(3, 0))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(4, 0))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(5, 0))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(6, 0))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(7, 0))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(8, 0))).set(border);

        //middle row
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(0, 1))).set(border);
        //inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(1, 1))).set(border);
        //inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(2, 1))).set(border);
        //inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(3, 1))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(4, 1))).set(border);
        //inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(5, 1))).set(border);
        //inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(6, 1))).set(border);
        //inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(7, 1))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(8, 1))).set(border);

        //bottom row
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(0, 2))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(1, 2))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(2, 2))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(3, 2))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(4, 2))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(5, 2))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(6, 2))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(7, 2))).set(border);
        inventory.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotPos.of(8, 2))).set(border);
    }
}
