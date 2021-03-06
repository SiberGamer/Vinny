package com.bot.tasks

import com.bot.ShardingManager
import com.bot.db.ScheduledCommandDAO
import com.bot.metrics.MetricsManager
import com.bot.models.ScheduledCommand
import com.bot.utils.Logger
import com.bot.utils.ScheduledCommandUtils
import net.dv8tion.jda.api.JDA
import org.slf4j.MDC

class RunScheduledCommandsDefferedTask() : Thread() {

    private val scheduledCommandDAO : ScheduledCommandDAO = ScheduledCommandDAO.getInstance()
    private val logger : Logger = Logger(RunScheduledCommandsDefferedTask::class.java.name)
    private val metrics : MetricsManager = MetricsManager.getInstance()

    override fun run() {
        logger.info("kicking off scheduled command run.")

        val scheduledCommands : List<ScheduledCommand> = scheduledCommandDAO.allScheduledCommands

        val startTime = System.currentTimeMillis()
        var commandRanCount = 0
        var failed_count = 0

        for (sCommand in scheduledCommands) {
            try {
                val shouldRun = System.currentTimeMillis() > sCommand.lastRun + sCommand.interval

                if (shouldRun) {
                    val jda: JDA = ScheduledCommandUtils.getShardForCommand(sCommand)
                    if (jda == null) {
                        logger.warning("Scheduled command guild not found in shards")
                        // We dont want to remove it in case vinny is running on multiple hosts/containers
                        continue
                    }
                    val event = ScheduledCommandUtils.generateSimulatedMessageRecievedEvent(sCommand, jda)
                    val client = ShardingManager.getInstance().commandClientImpl
                    client.onEvent(event)

                    // Commenting out due to feedback
                    // event.channel.sendMessage("> Command scheduled by " +
                    //        FormattingUtils.getUserNameOrId(sCommand.guild, sCommand.author)).queueAfter(2, TimeUnit.SECONDS)

                    scheduledCommandDAO.updateLastRun(sCommand.id)
                    metrics.markScheduledCommandRan(sCommand)
                    commandRanCount++
                }
            } catch (e: Exception) {
                MDC.put("commandId", "" + sCommand.id)
                logger.warning("Failed to run scheduled command.", e)
                scheduledCommandDAO.recordFailure(sCommand, e.toString())
                failed_count++
                MDC.clear()
            }
        }

        val runtime = System.currentTimeMillis() - startTime
        scheduledCommandDAO.recordRunComplete(commandRanCount, runtime, failed_count)
        logger.info("Finished scheduled command task. Commands run: $commandRanCount")
    }
}