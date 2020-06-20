package io.github.polymeta.generations;

import com.google.inject.Inject;
import io.github.polymeta.common.GUI.PartyGUI;
import io.github.polymeta.common.command.GiveItemCMD;
import io.github.polymeta.common.config.GeneralConfig;
import io.github.polymeta.common.config.GeneralConfigManager;
import io.github.polymeta.common.listener.SpongeListener;
import io.github.polymeta.generations.adapter.GenerationsAdapter;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

@Plugin(id = "pixelmonshiny",
        name = "PixelmonShiny Generations",
        description = "Turn your pokemon shiny with an item!",
        authors = {"Polymeta/BlackSpirit"},
        version = "1.0.0",
        dependencies = @Dependency(id = "pixelmon"))
public class PixelShinyGenerations
{
    @Inject
    private Logger logger;

    @DefaultConfig(sharedRoot = false)
    @Inject
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;

    private GeneralConfigManager configManager;

    @Listener
    public void onInit(GameInitializationEvent event)
    {
        configManager = new GeneralConfigManager(configLoader);
        Sponge.getEventManager().registerListeners(this, new SpongeListener(configManager));
        PartyGUI.initGUI(this, configManager, new GenerationsAdapter());

        CommandSpec giveItem = CommandSpec.builder()
                .description(Text.of("Give a Shiny item to a player!"))
                .permission("pixelmonshiny.give")
                .executor(new GiveItemCMD(configManager))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.playerOrSource(Text.of("player"))),
                        GenericArguments.optionalWeak(GenericArguments.integer(Text.of("amount")))
                )
                .build();
        Sponge.getCommandManager().register(this, giveItem, "giveshinyitem", "gsi");

        logger.info("PixelShiny by Polymeta, ready!");
    }

    public GeneralConfig getConfig()
    {
        return configManager.getConfig();
    }
}
