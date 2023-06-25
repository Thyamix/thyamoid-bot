package com.thyamix.commands;

import com.thyamix.model.ServerSettings;
import com.thyamix.utils.commands.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class EnableLoggerCommand extends Command {
    private final @NotNull ServerSettings serverSettings;

    public EnableLoggerCommand(@NotNull ServerSettings serverSettings) {
        super("enablelogger");

        this.serverSettings = serverSettings;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        if (this.serverSettings.getLogChannelId() != -1) {
            if (this.serverSettings.getLoggerEnabled()) {
                event.reply("Logger is already enabled").queue();
            } else {
                this.serverSettings.setLoggerEnabled(true);
                event.reply("Logger has been enabled").queue();
            }
        } else {
            event.reply("You first need to set a logs channel \nTry using /setlogchannel <Channel Name>").queue();
        }
    }

    @Override
    public @NotNull CommandData createCommandData() {
        return Commands.slash("enablelogger", "Enable the logger")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
