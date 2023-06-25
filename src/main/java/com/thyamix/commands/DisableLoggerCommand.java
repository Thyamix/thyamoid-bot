package com.thyamix.commands;

import com.thyamix.model.ServerSettings;
import com.thyamix.utils.commands.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class DisableLoggerCommand extends Command {
    private final @NotNull ServerSettings serverSettings;

    public DisableLoggerCommand(@NotNull ServerSettings serverSettings) {
        super("disablelogger");

        this.serverSettings = serverSettings;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        if (this.serverSettings.getLoggerEnabled()) {
            this.serverSettings.setLoggerEnabled(false);
            event.reply("Logger has been disabled").queue();
        } else {
            event.reply("Logger is already disabled").queue();
        }
    }

    @Override
    public @NotNull CommandData createCommandData() {
        return Commands.slash("disablelogger", "Disables the Logger")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
