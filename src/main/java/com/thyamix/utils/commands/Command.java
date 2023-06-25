package com.thyamix.utils.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

public abstract class Command {
    private final @NotNull String commandName;

    protected Command(@NotNull String commandName) {
        this.commandName = commandName;
    }

    public abstract void execute(@NotNull SlashCommandInteractionEvent event);

    public abstract @NotNull CommandData createCommandData();

    public @NotNull String getCommandName() {
        return this.commandName;
    }
}
