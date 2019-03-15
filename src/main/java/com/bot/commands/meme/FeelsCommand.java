package com.bot.commands.meme;

import com.bot.commands.MemeCommand;
import com.bot.utils.CommandPermissions;
import com.jagrosh.jdautilities.command.CommandEvent;

public class FeelsCommand extends MemeCommand {

    private String feels = "───────▄▀▀▀▀▀▀▀▀▀▀▄▄\n" +
            "────▄▀▀░░░░░░░░░░░░░▀▄\n" +
            "──▄▀░░░░░░░░░░░░░░░░░░▀▄\n" +
            "──█░░░░░░░░░░░░░░░░░░░░░▀▄\n" +
            "─▐▌░░░░░░░░▄▄▄▄▄▄▄░░░░░░░▐▌\n" +
            "─█░░░░░░░░░░░▄▄▄▄░░▀▀▀▀▀░░█\n" +
            "▐▌░░░░░░░▀▀▀▀░░░░░▀▀▀▀▀░░░▐▌\n" +
            "█░░░░░░░░░▄▄▀▀▀▀▀░░░░▀▀▀▀▄░█\n" +
            "█░░░░░░░░░░░░░░░░▀░░░▐░░░░░▐▌\n" +
            "▐▌░░░░░░░░░▐▀▀██▄░░░░░░▄▄▄░▐▌\n" +
            "─█░░░░░░░░░░░▀▀▀░░░░░░▀▀██░░█\n" +
            "─▐▌░░░░▄░░░░░░░░░░░░░▌░░░░░░█\n" +
            "──▐▌░░▐░░░░░░░░░░░░░░▀▄░░░░░█\n" +
            "───█░░░▌░░░░░░░░▐▀░░░░▄▀░░░▐▌\n" +
            "───▐▌░░▀▄░░░░░░░░▀░▀░▀▀░░░▄▀\n" +
            "───▐▌░░▐▀▄░░░░░░░░░░░░░░░░█\n" +
            "───▐▌░░░▌░▀▄░░░░▀▀▀▀▀▀░░░█\n" +
            "───█░░░▀░░░░▀▄░░░░░░░░░░▄▀\n" +
            "──▐▌░░░░░░░░░░▀▄░░░░░░▄▀\n" +
            "─▄▀░░░▄▀░░░░░░░░▀▀▀▀█▀\n" +
            "▀░░░▄▀░░░░░░░░░░▀░░░▀▀▀▀▄▄▄▄▄";

    public FeelsCommand() {
        this.name = "feels";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        metricsManager.markCommand(this, commandEvent.getAuthor(), commandEvent.getGuild());
        // Check the permissions to do the command
        if (!CommandPermissions.canExecuteCommand(this, commandEvent))
            return;

        commandEvent.reply(feels);
    }
}