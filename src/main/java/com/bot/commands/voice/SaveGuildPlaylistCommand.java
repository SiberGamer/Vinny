package com.bot.commands.voice;

import com.bot.Bot;
import com.bot.db.PlaylistDAO;
import com.bot.utils.CommandCategories;
import com.bot.utils.CommandPermissions;
import com.bot.voice.QueuedAudioTrack;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SaveGuildPlaylistCommand extends Command {
	private static final Logger LOGGER = Logger.getLogger(SaveGuildPlaylistCommand.class.getName());
	private PlaylistDAO playlistDAO;
	private Bot bot;

	public SaveGuildPlaylistCommand(Bot bot) {
		this.name = "savegplaylist";
		this.arguments = "<playlist name>";
		this.help = "Saves all of the currently queued tracks into a playlist tied to your guild.";
		this.category = CommandCategories.VOICE;

		this.playlistDAO = PlaylistDAO.getInstance();
		this.bot = bot;
	}

	@Override
	protected void execute(CommandEvent commandEvent) {
		// Check the permissions to do the command
		if (!CommandPermissions.canExecuteCommand(this, commandEvent))
			return;

		String args = commandEvent.getArgs();
		if (args.equals("")) {
			commandEvent.reply("You need to specify a name for the playlist.");
			return;
		}

		LinkedList<QueuedAudioTrack> tracks = new LinkedList<>(bot.getHandler(commandEvent.getGuild()).getTracks());
		QueuedAudioTrack nowPlaying = bot.getHandler(commandEvent.getGuild()).getNowPlaying();

		List<QueuedAudioTrack> trackList = new LinkedList<>();
		trackList.add(nowPlaying);
		trackList.addAll(tracks);

		if (playlistDAO.createPlaylistForGuild(commandEvent.getGuild().getId(), args, trackList)) {
			commandEvent.reply("Playlist successfully created.");
		} else {
			commandEvent.reply("Something went wrong! Failed to create playlist.");
		}
	}
}