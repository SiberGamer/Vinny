package com.bot.commands.general

import com.bot.commands.GeneralCommand
import com.jagrosh.jdautilities.command.CommandEvent

class PingCommand : GeneralCommand() {
    init {
        this.name = "ping"
        this.guildOnly = false
        this.help = "Gets the ping from Vinny to discord."
    }

    override fun executeCommand(commandEvent: CommandEvent) {
        commandEvent.reply(commandEvent.jda.gatewayPing.toString() + "ms")
    }
}
