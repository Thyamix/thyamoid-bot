package com.thyamix;

import com.thyamix.commands.DisableLoggerCommand;
import com.thyamix.commands.EnableLoggerCommand;
import com.thyamix.commands.SetLogChannelCommand;
import com.thyamix.commands.TestCommand;
import com.thyamix.discordlogger.DiscordLoggerManager;
import com.thyamix.model.ServerSettings;
import com.thyamix.storage.ServerSettingsStorage;
import com.thyamix.utils.commands.Command;
import com.thyamix.utils.commands.CommandBackend;
import com.thyamix.utils.concurrency.ThreadUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DiscordBot {
    private static final String TOKEN = "MTEyMTE1MDY3MjUyODc1Mjc4MA.GRkzur.6JSWFID-kwai6skNxeaYZSypUoc6RQm3e3A7Rg";
    private final ServerSettings serverSettings;
    private final DiscordLoggerManager discordLoggerManager;
    private final JDA api;

    public DiscordBot() throws InterruptedException {
        this.api = JDABuilder.createDefault(TOKEN).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build().awaitReady();

        ServerSettingsStorage storage = new ServerSettingsStorage();
        this.serverSettings = storage.load();

        List<Command> commands = this.createCommands();
        CommandBackend commandBackend = new CommandBackend(api, commands);

        api.addEventListener(commandBackend);

        ThreadUtils.SCHEDULER.scheduleAtFixedRate(() -> storage.save(this.serverSettings), 1, 1, TimeUnit.MINUTES);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> storage.save(this.serverSettings)));

        this.discordLoggerManager = new DiscordLoggerManager(this.serverSettings, this.api);

        api.addEventListener(this.discordLoggerManager);
    }


    private List<Command> createCommands() {
        return List.of(
                new TestCommand(),
                new SetLogChannelCommand(this.serverSettings),
                new EnableLoggerCommand(this.serverSettings),
                new DisableLoggerCommand(this.serverSettings)
        );
    }
}
