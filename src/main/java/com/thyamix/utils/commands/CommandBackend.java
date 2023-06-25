package com.thyamix.utils.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandBackend extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandBackend.class);

    // Map<Command ID, Command>
    private final @NotNull Map<String, Command> commands;

    public CommandBackend(@NotNull JDA jda, @NotNull List<Command> commands) {
        this.commands = commands.stream()
                .collect(Collectors.toUnmodifiableMap(Command::getCommandName, cmd -> cmd));

        this.registerCommands(jda, commands);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        Command command = this.commands.get(commandName);
        if (command == null) {
            LOGGER.warn("Received a SlashCommandInteractionEvent for unknown command (name: {}, id: {})",
                    event.getName(), event.getCommandId());

            return;
        }

        command.execute(event);
    }

    private void registerCommands(@NotNull JDA jda, @NotNull List<Command> commands) {
        List<CommandData> commandDataList = commands.stream().map(Command::createCommandData).toList();

        jda.getGuildById(1081960729072111766L).updateCommands().addCommands(commandDataList)
                .queue(success -> LOGGER.info("Registered {} commands with Discord API", commands.size()),
                        ex -> LOGGER.error("Failed to register commands: ", ex));
    }
}
