package io.github.polymeta.common.command;

import io.github.polymeta.common.config.GeneralConfigManager;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.plugin.meta.util.NonnullByDefault;

public class GiveItemCMD implements CommandExecutor 
{
    private GeneralConfigManager configManager;

    public GiveItemCMD(GeneralConfigManager configManager)
    {
        this.configManager = configManager;
    }

    @Override
    @NonnullByDefault
    @NonNull
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        Player player = args.<Player>getOne("player").orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Couldn't find player!")));
        int amount = args.<Integer>getOne("amount").orElse(1);

        ItemStack shinyItem = configManager.getShinyItem();
        shinyItem.setQuantity(amount);
        InventoryTransactionResult result = player.getInventory().offer(shinyItem);
        if(result.getType().equals(InventoryTransactionResult.Type.SUCCESS))
        {
            src.sendMessage(Text.of(TextColors.GREEN, "Successfully gave " + amount + "x ShinyItem" + (amount == 1 ? "": "s") + " to " + player.getName()));
        }
        else{
            src.sendMessage(Text.of(TextColors.RED, "Something went wrong while trying to give the ShinyItem to " + player.getName() + "!"));
        }
        return CommandResult.success();
    }
}
