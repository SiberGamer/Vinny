package com.bot.commands.voice;

import com.bot.utils.CommandCategories;
import com.bot.utils.CommandPermissions;
import com.bot.voice.VoiceSendHandler;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.logging.Logger;

public class PauseCommand extends Command {
	private static final Logger LOGGER = Logger.getLogger(PauseCommand.class.getName());

	public PauseCommand() {
		this.name = "pause";
		this.arguments = "";
		this.help = "Pauses or resumes the stream";
		this.category = CommandCategories.VOICE;
	}

	@Override
	protected void execute(CommandEvent commandEvent) {
		// Check the permissions to do the command
		if (!CommandPermissions.canExecuteCommand(this, commandEvent))
			return;

		VoiceSendHandler handler = (VoiceSendHandler) commandEvent.getGuild().getAudioManager().getSendingHandler();
		if (handler == null) {
			commandEvent.reply(commandEvent.getClient().getWarning() + " I am not connected to a voice channel.");
		}
		else {
			if (handler.getPlayer().isPaused()) {
				handler.getPlayer().setPaused(false);
				commandEvent.reply(commandEvent.getClient().getSuccess() + " Resumed stream.");
			}
			else {
				handler.getPlayer().setPaused(true);
				commandEvent.reply(commandEvent.getClient().getSuccess() + " Paused stream.");
			}
		}
	}
}