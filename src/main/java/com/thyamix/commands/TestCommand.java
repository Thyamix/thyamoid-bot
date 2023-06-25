package com.thyamix.commands;

import com.thyamix.utils.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends Command {

    public TestCommand() {
        super("test");
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        event.reply("Test").queue();
    }

    @Override
    public @NotNull CommandData createCommandData() {
        return Commands.slash("test", "Test Command");
    }
}
