package com.thyamix.commands;

import com.thyamix.model.ServerSettings;
import com.thyamix.utils.commands.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class SetLogChannelCommand extends Command {
    private final ServerSettings serverSettings;

    public SetLogChannelCommand(@NotNull ServerSettings serverSettings) {
        super("setlogchannel");

        this.serverSettings = serverSettings;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping channelOptionData = event.getOption("channel");

        TextChannel channel;
        if (channelOptionData == null) {
            channel = event.getChannel().asTextChannel();
        } else {
            channel = event.getOption("channel").getAsChannel().asTextChannel();
        }
        serverSettings.setLogChannelId(channel.getIdLong());
        event.reply("Channel Set to %s".formatted(channel.getAsMention())).queue();
    }

    @Override
    public @NotNull CommandData createCommandData() {
        return Commands.slash("setlogchannel", "Sets the Log Channel")
                .addOptions(new OptionData(OptionType.CHANNEL, "channel", "Channel", false)
                        .setChannelTypes(ChannelType.TEXT))
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
