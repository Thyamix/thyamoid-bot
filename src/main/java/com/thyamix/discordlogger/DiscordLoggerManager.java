package com.thyamix.discordlogger;

import com.thyamix.model.ServerSettings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class DiscordLoggerManager extends ListenerAdapter {

    private final ServerSettings serverSettings;
    private final JDA api;

    public DiscordLoggerManager(ServerSettings serverSettings, JDA api) {
        this.serverSettings = serverSettings;
        this.api = api;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);

        if (this.serverSettings.getLoggerEnabled()) {
            User user = event.getMember().getUser();

            TextChannel logChannel = api.getTextChannelById(this.serverSettings.getLogChannelId());

            EmbedBuilder embedBuilder = new EmbedBuilder();

            String description = "**<@" + user.getId() + "> joined the server " + event.getGuild().getName() + "**";

            embedBuilder.setAuthor(user.getName(), null, user.getDefaultAvatarUrl())
                    .setDescription(description)
                    .setTimestamp(Instant.now())
                    .setFooter(event.getGuild().getName());

            logChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        super.onGuildMemberRemove(event);

        if (this.serverSettings.getLoggerEnabled()) {
            User user = event.getMember().getUser();

            TextChannel logChannel = api.getTextChannelById(this.serverSettings.getLogChannelId());

            EmbedBuilder embedBuilder = new EmbedBuilder();

            String description = "**<@" + user.getId() + "> left the server " + event.getGuild().getName() + "**";

            embedBuilder.setAuthor(user.getName(), null, user.getDefaultAvatarUrl())
                    .setDescription(description)
                    .setTimestamp(Instant.now())
                    .setFooter(event.getGuild().getName());

            logChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        super.onGuildVoiceUpdate(event);

        if (this.serverSettings.getLoggerEnabled()) {
            User user = event.getMember().getUser();

            TextChannel logChannel = api.getTextChannelById(this.serverSettings.getLogChannelId());

            EmbedBuilder embedBuilder = new EmbedBuilder();

            String description;

            if (event.getChannelLeft() == null && event.getChannelJoined() != null) {
                description = "**<@" + user.getId() + "> joined voice channel " + event.getChannelJoined().getJumpUrl() + "**";
            } else if (event.getChannelJoined() == null && event.getChannelLeft() != null) {
                description = "**<@" + user.getId() + "> left voice channel " + event.getChannelLeft().getJumpUrl() + "**";
            } else {
                description = "**" + event.getMember().getAsMention() + " switched from voice channel " +
                        event.getChannelLeft().getJumpUrl() + " to " +
                        event.getChannelJoined().getJumpUrl() + "**";
            }
            embedBuilder.setAuthor(user.getName(), null, user.getDefaultAvatarUrl())
                    .setDescription(description)
                    .setTimestamp(Instant.now())
                    .setFooter(event.getGuild().getName());

            logChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        super.onMessageDelete(event);

        if (this.serverSettings.getLoggerEnabled()) {
            TextChannel logChannel = api.getTextChannelById(this.serverSettings.getLogChannelId());
            EmbedBuilder embedBuilder = new EmbedBuilder();
            String description;
            Channel channel = event.getChannel().asTextChannel();

            description = "A message was deleted from " + channel.getAsMention() + " with Message-ID: " + event.getMessageId();

            embedBuilder.setDescription(description)
                    .setTimestamp(Instant.now())
                    .setFooter(event.getGuild().getName());

            logChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (this.serverSettings.getLoggerEnabled()) {
            Channel channel = event.getChannel().asTextChannel();
            TextChannel logChannel = api.getTextChannelById(this.serverSettings.getLogChannelId());
            User user = event.getMember().getUser();
            if (logChannel != channel) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                String description;

                description = ":incoming_envelope: A message was sent on " + channel.getAsMention() + " by " + event.getAuthor().getAsMention() + " with Message-ID: " + event.getMessageId();


                embedBuilder.setAuthor(user.getName(), null, user.getDefaultAvatarUrl())
                        .setDescription(description)
                        .addField("Message", event.getMessage().getContentDisplay(), false)
                        .setTimestamp(Instant.now())
                        .setFooter(event.getGuild().getName());

                logChannel.sendMessageEmbeds(embedBuilder.build()).queue();
            }
        }
    }
}
