package dev.bingley.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.bingley.BattleManager
import dev.bingley.CardClash
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.commands.Commands as CommandManager

object ListBattlesCmd {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                CommandManager.literal("listbattles")
                    .executes { context ->
                        execute(context)
                    }
            )
        }
    }

    private fun execute(context: CommandContext<CommandSourceStack>): Int {
        val battleManager = CardClash.battleManager
        val result = battleManager.getBattles()
    }
}